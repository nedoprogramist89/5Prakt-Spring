package com.example.project2.controller;

import com.example.project2.dto.OrderRequest;
import com.example.project2.model.Order;
import com.example.project2.model.User;
import com.example.project2.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Order Controller", description = "API для управления заказами")
public class OrderController {
    
    private final OrderService orderService;
    
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    
    @GetMapping
    @Operation(summary = "Получить все заказы", description = "Возвращает список всех заказов")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.findAllOrders());
    }
    
    @GetMapping("/async")
    @Operation(summary = "Получить все заказы асинхронно", description = "Асинхронно возвращает список всех заказов")
    public CompletableFuture<ResponseEntity<List<Order>>> getAllOrdersAsync() {
        return orderService.findAllOrdersAsync()
                .thenApply(ResponseEntity::ok);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Получить заказ по ID", description = "Возвращает заказ с указанным ID")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findOrderById(id));
    }
    
    @GetMapping("/{id}/async")
    @Operation(summary = "Получить заказ по ID асинхронно", description = "Асинхронно возвращает заказ с указанным ID")
    public CompletableFuture<ResponseEntity<Order>> getOrderByIdAsync(@PathVariable Long id) {
        return orderService.findOrderByIdAsync(id)
                .thenApply(ResponseEntity::ok);
    }
    
    @GetMapping("/user/{userId}")
    @Operation(summary = "Получить заказы пользователя", description = "Возвращает все заказы указанного пользователя")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.findOrdersByUserId(userId));
    }
    
    @GetMapping("/user/{userId}/async")
    @Operation(summary = "Получить заказы пользователя асинхронно", description = "Асинхронно возвращает все заказы указанного пользователя")
    public CompletableFuture<ResponseEntity<List<Order>>> getOrdersByUserIdAsync(@PathVariable Long userId) {
        return orderService.findOrdersByUserIdAsync(userId)
                .thenApply(ResponseEntity::ok);
    }
    
    @PostMapping
    @Operation(summary = "Создать заказ", description = "Создает новый заказ. Принимает OrderRequest с userId или полный объект Order")
    public ResponseEntity<Order> createOrder(@Valid @RequestBody OrderRequest orderRequest) {
        Order order = new Order();
        order.setTitle(orderRequest.getTitle());
        order.setPrice(orderRequest.getPrice());
        
        User user = new User();
        user.setId(orderRequest.getUserId());
        order.setUser(user);
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.createOrder(order));
    }
    
    @PostMapping("/async")
    @Operation(summary = "Создать заказ асинхронно", description = "Асинхронно создает новый заказ")
    public CompletableFuture<ResponseEntity<Order>> createOrderAsync(@Valid @RequestBody Order order) {
        return orderService.createOrderAsync(order)
                .thenApply(o -> ResponseEntity.status(HttpStatus.CREATED).body(o));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Обновить заказ", description = "Обновляет информацию о заказе")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @Valid @RequestBody Order order) {
        return ResponseEntity.ok(orderService.updateOrder(id, order));
    }
    
    @PutMapping("/{id}/async")
    @Operation(summary = "Обновить заказ асинхронно", description = "Асинхронно обновляет информацию о заказе")
    public CompletableFuture<ResponseEntity<Order>> updateOrderAsync(@PathVariable Long id, @Valid @RequestBody Order order) {
        return orderService.updateOrderAsync(id, order)
                .thenApply(ResponseEntity::ok);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить заказ", description = "Удаляет заказ по ID")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/{id}/async")
    @Operation(summary = "Удалить заказ асинхронно", description = "Асинхронно удаляет заказ по ID")
    public CompletableFuture<ResponseEntity<Void>> deleteOrderAsync(@PathVariable Long id) {
        return orderService.deleteOrderAsync(id)
                .thenApply(v -> ResponseEntity.noContent().build());
    }
}

