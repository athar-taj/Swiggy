package cln.swiggy.payment.serviceImpl;

import cln.swiggy.payment.model.Payment;
import cln.swiggy.payment.model.SagaEventEntity;
import cln.swiggy.payment.model.enums.EventType;
import cln.swiggy.payment.repository.SagaRepository;
import cln.swiggy.payment.serviceImpl.OtherImpl.EntityValidator;
import cln.swiggy.payment.serviceImpl.OtherImpl.IDGenerator;
import cln.swiggy.payment.serviceImpl.OtherImpl.NotificationUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cln.swiggy.payment.model.enums.PaymentStatus;
import cln.swiggy.payment.model.request.PaymentRequest;
import cln.swiggy.payment.model.response.CommonResponse;
import cln.swiggy.payment.service.PaymentService;
import cln.swiggy.payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${rabbitmq.notification.restaurant.exchange}")
    private String notificationExchange;

    @Value("${rabbitmq.notification.restaurant.routing_Key}")
    private String notificationRoutingKey;

    @Autowired
    SagaRepository sagaRepository;
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    EntityValidator entityValidator;
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Override
    public ResponseEntity<CommonResponse> getPaymentById(Long paymentId) {
            Optional<Payment> payment = paymentRepository.findById(paymentId);
            if (payment.isPresent()) {
                return ResponseEntity.ok(new CommonResponse(HttpStatus.OK.value(),"Payment found successfully", payment.get()));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CommonResponse(HttpStatus.NOT_FOUND.value(), "Payment not found",null));
    }

    @Override
    public ResponseEntity<CommonResponse> createPayment(PaymentRequest paymentRequest,String sagaId) throws JsonProcessingException {
        entityValidator.validateUserExists(paymentRequest.getUserId());
        entityValidator.validateOrderExists(paymentRequest.getOrderId());

        String eventId = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();

        Payment payment = new Payment();
        payment.setOrderId(paymentRequest.getOrderId());
        payment.setTransactionId(IDGenerator.generateId());
        payment.setAmount(paymentRequest.getAmount());
        payment.setUserId(paymentRequest.getUserId());
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());
        payment.setPaymentStatus(PaymentStatus.PENDING);

        try {
            Payment savedPayment = paymentRepository.save(payment);
            SagaEventEntity entity = new SagaEventEntity();

            Map<String, Object> payload = new HashMap<>();
            payload.put("paymentId", savedPayment.getId());
            payload.put("amount", savedPayment.getAmount());
            payload.put("paymentMethod", savedPayment.getPaymentMethod());

            entity.setEventId(eventId);
            entity.setSagaId(sagaId);
            entity.setOrderId(paymentRequest.getOrderId());
            entity.setEventType(EventType.PAYMENT_SUCCESS);
            entity.setSourceService("PAYMENT");
            entity.setEventTime(now);
            entity.setPayloadJson(new ObjectMapper().writeValueAsString(payload));
            sagaRepository.save(entity);

            rabbitTemplate.convertAndSend("saga_exchange", "saga_key", entity);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new CommonResponse(HttpStatus.CREATED.value(), "Payment created successfully", savedPayment));

        } catch (Exception e) {
            Payment savedPayment = paymentRepository.save(payment);
            SagaEventEntity entity = new SagaEventEntity();

            Map<String, Object> payload = new HashMap<>();
            payload.put("paymentId", savedPayment.getId());
            payload.put("amount", savedPayment.getAmount());
            payload.put("paymentMethod", savedPayment.getPaymentMethod());

            entity.setEventId(eventId);
            entity.setSagaId(sagaId);
            entity.setOrderId(paymentRequest.getOrderId());
            entity.setEventType(EventType.PAYMENT_FAILED);
            entity.setSourceService("PAYMENT");
            entity.setEventTime(now);
            entity.setPayloadJson(new ObjectMapper().writeValueAsString(payload));
            sagaRepository.save(entity);

            rabbitTemplate.convertAndSend("saga_exchange", "saga_key", entity);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CommonResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Payment failed", null));
        }
    }

    @Override
    public ResponseEntity<CommonResponse> getPaymentByOrderId(Long orderId) {

            entityValidator.validateOrderExists(orderId);

            Optional<Payment> payment = paymentRepository.findByOrderId(orderId);
            if (payment.isPresent()) {
                return ResponseEntity.ok(new CommonResponse(HttpStatus.OK.value(),"Payment found successfully", payment.get()));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CommonResponse(HttpStatus.NOT_FOUND.value(),"Payment not found for order",null));

    }

    @Override
    public ResponseEntity<CommonResponse> getUserPayments(Long userId) {

            entityValidator.validateUserExists(userId);
            List<Payment> payments = paymentRepository.findByUserId(userId);
            if (!payments.isEmpty()) {
                return ResponseEntity.ok(new CommonResponse(HttpStatus.OK.value(),"Payments found successfully", payments));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CommonResponse(HttpStatus.NOT_FOUND.value(),"No payments found for user", null));
    }

    @Override
    public ResponseEntity<CommonResponse> getPaymentsByStatus(PaymentStatus status) {
            List<Payment> payments = paymentRepository.findByPaymentStatus(status);
            if (!payments.isEmpty()) {
                return ResponseEntity.ok(new CommonResponse(HttpStatus.OK.value(),"Payments found successfully", payments));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CommonResponse(HttpStatus.NOT_FOUND.value(),"No payments found with status: " + status, null));
    }
}