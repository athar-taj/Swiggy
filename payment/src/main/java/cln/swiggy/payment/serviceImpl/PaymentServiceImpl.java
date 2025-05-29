package cln.swiggy.payment.serviceImpl;

import cln.swiggy.payment.model.Payment;
import cln.swiggy.payment.serviceImpl.OtherImpl.EntityValidator;
import cln.swiggy.payment.serviceImpl.OtherImpl.IDGenerator;
import cln.swiggy.payment.serviceImpl.OtherImpl.NotificationUtil;
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
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${rabbitmq.notification.restaurant.exchange}")
    private String notificationExchange;

    @Value("${rabbitmq.notification.restaurant.routing_Key}")
    private String notificationRoutingKey;


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
    public ResponseEntity<CommonResponse> createPayment(PaymentRequest paymentRequest) {

            entityValidator.validateUserExists(paymentRequest.getUserId());
            entityValidator.validateOrderExists(paymentRequest.getOrderId());

            Payment payment = new Payment();
            payment.setOrderId(paymentRequest.getOrderId());
            payment.setTransactionId(IDGenerator.generateId());
            payment.setAmount(paymentRequest.getAmount());
            payment.setUserId(paymentRequest.getUserId());
            payment.setPaymentStatus(PaymentStatus.PENDING);
            payment.setPaymentMethod(paymentRequest.getPaymentMethod());

            Payment savedPayment = paymentRepository.save(payment);

//            HashMap<String, Object> notificationData = NotificationUtil.getNotificationData(restaurant.getId(), "RESTAURANT","PAYMENT_CREATE", LocalDateTime.now());
//            rabbitTemplate.convertAndSend(notificationExchange, notificationRoutingKey, notificationData);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new CommonResponse(HttpStatus.CREATED.value(),"Payment created successfully", savedPayment));
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