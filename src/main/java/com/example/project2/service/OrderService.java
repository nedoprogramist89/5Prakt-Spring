package com.example.project2.service;

import com.example.project2.model.Order;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface OrderService {
    List<Order> findAllOrders();
    CompletableFuture<List<Order>> findAllOrdersAsync();
    Order findOrderById(Long id);
    CompletableFuture<Order> findOrderByIdAsync(Long id);
    List<Order> findOrdersByUserId(Long userId);
    CompletableFuture<List<Order>> findOrdersByUserIdAsync(Long userId);
    Order createOrder(Order order);
    CompletableFuture<Order> createOrderAsync(Order order);
    Order updateOrder(Long id, Order order);
    CompletableFuture<Order> updateOrderAsync(Long id, Order order);
    void deleteOrder(Long id);
    CompletableFuture<Void> deleteOrderAsync(Long id);
}

