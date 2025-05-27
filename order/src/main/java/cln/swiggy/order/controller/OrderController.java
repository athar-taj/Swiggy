package cln.swiggy.order.controller;

import cln.swiggy.order.model.enums.OrderStatus;
import cln.swiggy.order.model.request.OrderRequest;
import cln.swiggy.order.model.response.CommonResponse;
import cln.swiggy.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Order Management", description = "APIs for managing orders")
public class OrderController {

    @Autowired OrderService orderService;

    @Operation(summary = "Create a new order", description = "Creates a new order in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order created successfully",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<CommonResponse> createOrder(
            @Parameter(description = "Order details", required = true)
            @RequestBody OrderRequest request) {
        return orderService.createOrder(request);
    }

    @Operation(summary = "Get order by ID", description = "Retrieves an order by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order found",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @GetMapping("/{orderId}")
    public ResponseEntity<CommonResponse> getOrderById(
            @Parameter(description = "ID of the order to retrieve", required = true)
            @PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @Operation(summary = "Update an order", description = "Updates an existing order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order updated successfully",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "404", description = "Order not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/{orderId}")
    public ResponseEntity<CommonResponse> updateOrder(
            @Parameter(description = "ID of the order to update", required = true)
            @PathVariable Long orderId,
            @Parameter(description = "Updated order details", required = true)
            @RequestBody OrderRequest request) {
        return orderService.updateOrder(orderId, request);
    }

    @Operation(summary = "Cancel an order", description = "Cancels an existing order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order cancelled successfully",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @DeleteMapping("/{orderId}")
    public ResponseEntity<CommonResponse> cancelOrder(
            @Parameter(description = "ID of the order to cancel", required = true)
            @PathVariable Long orderId) {
        return orderService.cancelOrder(orderId);
    }

    @Operation(summary = "Get user orders", description = "Retrieves all orders for a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders retrieved successfully",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<CommonResponse> getUserOrders(
            @Parameter(description = "ID of the user", required = true)
            @PathVariable Long userId) {
        return orderService.getUsersOrders(userId);
    }

    @Operation(summary = "Get restaurant orders", description = "Retrieves all orders for a specific restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders retrieved successfully",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<CommonResponse> getRestaurantOrders(
            @Parameter(description = "ID of the restaurant", required = true)
            @PathVariable Long restaurantId) {
        return orderService.getRestaurantOrders(restaurantId);
    }

    @Operation(summary = "Get restaurant orders by status",
            description = "Retrieves orders for a specific restaurant filtered by status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders retrieved successfully",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "404", description = "Restaurant not found"),
            @ApiResponse(responseCode = "400", description = "Invalid status")
    })
    @GetMapping("/restaurant/{restaurantId}/status")
    public ResponseEntity<CommonResponse> getRestaurantOrdersByStatus(
            @Parameter(description = "ID of the restaurant", required = true)
            @PathVariable Long restaurantId,
            @Parameter(description = "Order status to filter by", required = true)
            @RequestParam("Status") OrderStatus status) {
        return orderService.getRestaurantOrdersByStatus(restaurantId, status);
    }
}