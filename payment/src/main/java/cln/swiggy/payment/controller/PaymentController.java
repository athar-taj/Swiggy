package cln.swiggy.payment.controller;

import cln.swiggy.payment.model.enums.PaymentStatus;
import cln.swiggy.payment.model.request.PaymentRequest;
import cln.swiggy.payment.model.response.CommonResponse;
import cln.swiggy.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@Tag(name = "Payment Controller", description = "APIs for managing payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/{paymentId}")
    @Operation(summary = "Get payment by ID",
            description = "Retrieves payment details for the given payment ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment found successfully"),
            @ApiResponse(responseCode = "404", description = "Payment not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CommonResponse> getPaymentById(
            @Parameter(description = "ID of the payment", required = true)
            @PathVariable Long paymentId) {
        return paymentService.getPaymentById(paymentId);
    }

    @PostMapping
    @Operation(summary = "Create new payment",
            description = "Creates a new payment with the provided payment details")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Payment created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CommonResponse> createPayment(
            @Parameter(description = "Payment request details", required = true)
            @RequestBody PaymentRequest paymentRequest) {
        return paymentService.createPayment(paymentRequest);
    }

    @GetMapping("/order/{orderId}")
    @Operation(summary = "Get payment by order ID",
            description = "Retrieves payment details associated with the given order ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment found successfully"),
            @ApiResponse(responseCode = "404", description = "Payment not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CommonResponse> getPaymentByOrderId(
            @Parameter(description = "ID of the order", required = true)
            @PathVariable Long orderId) {
        return paymentService.getPaymentByOrderId(orderId);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get user payments",
            description = "Retrieves all payments associated with the given user ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payments retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CommonResponse> getUserPayments(
            @Parameter(description = "ID of the user", required = true)
            @PathVariable Long userId) {
        return paymentService.getUserPayments(userId);
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get payments by status",
            description = "Retrieves all payments with the specified payment status")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payments retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid status"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CommonResponse> getPaymentsByStatus(
            @Parameter(description = "Payment status", required = true)
            @PathVariable PaymentStatus status) {
        return paymentService.getPaymentsByStatus(status);
    }
}