package cln.swiggy.payment.service;

import cln.swiggy.payment.model.enums.PaymentStatus;
import cln.swiggy.payment.model.request.PaymentRequest;
import cln.swiggy.payment.model.response.CommonResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

public interface PaymentService {
    ResponseEntity<CommonResponse> getPaymentById(Long paymentId);
    ResponseEntity<CommonResponse> createPayment(PaymentRequest paymentRequest,String sagaId) throws JsonProcessingException;
    ResponseEntity<CommonResponse> getPaymentByOrderId(Long orderId);
    ResponseEntity<CommonResponse> getUserPayments(Long userId);
    ResponseEntity<CommonResponse> getPaymentsByStatus(PaymentStatus status);
}