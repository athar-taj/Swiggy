package cln.swiggy.payment.serviceImpl;

import cln.swiggy.payment.model.Payment;
import cln.swiggy.payment.serviceImpl.OtherImpl.EntityValidator;
import cln.swiggy.payment.serviceImpl.OtherImpl.IDGenerator;
import org.springframework.stereotype.Service;

import cln.swiggy.payment.model.enums.PaymentStatus;
import cln.swiggy.payment.model.request.PaymentRequest;
import cln.swiggy.payment.model.response.CommonResponse;
import cln.swiggy.payment.service.PaymentService;
import cln.swiggy.payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    EntityValidator entityValidator;

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