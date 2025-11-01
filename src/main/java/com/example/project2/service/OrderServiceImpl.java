package com.example.project2.service;

import com.example.project2.exception.ResourceNotFoundException;
import com.example.project2.model.Order;
import com.example.project2.model.User;
import com.example.project2.repository.OrderRepository;
import com.example.project2.repository.UserRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    
    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }
    
    @Override
    @Async
    @Transactional(readOnly = true)
    public CompletableFuture<List<Order>> findAllOrdersAsync() {
        return CompletableFuture.completedFuture(orderRepository.findAll());
    }
    
    @Override
    @Transactional(readOnly = true)
    public Order findOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Заказ с id " + id + " не найден"));
    }
    
    @Override
    @Async
    @Transactional(readOnly = true)
    public CompletableFuture<Order> findOrderByIdAsync(Long id) {
        return CompletableFuture.completedFuture(findOrderById(id));
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Order> findOrdersByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("Пользователь с id " + userId + " не найден");
        }
        return orderRepository.findByUserId(userId);
    }
    
    @Override
    @Async
    @Transactional(readOnly = true)
    public CompletableFuture<List<Order>> findOrdersByUserIdAsync(Long userId) {
        return CompletableFuture.completedFuture(findOrdersByUserId(userId));
    }
    
    @Override
    public Order createOrder(Order order) {
        if (order.getUser() == null || order.getUser().getId() == null) {
            throw new IllegalArgumentException("Пользователь обязателен для заказа");
        }
        User user = userRepository.findById(order.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь с id " + order.getUser().getId() + " не найден"));
        order.setUser(user);
        return orderRepository.save(order);
    }
    
    @Override
    @Async
    public CompletableFuture<Order> createOrderAsync(Order order) {
        return CompletableFuture.completedFuture(createOrder(order));
    }
    
    @Override
    public Order updateOrder(Long id, Order order) {
        Order existingOrder = findOrderById(id);
        existingOrder.setTitle(order.getTitle());
        existingOrder.setPrice(order.getPrice());
        if (order.getUser() != null && order.getUser().getId() != null) {
            User user = userRepository.findById(order.getUser().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Пользователь с id " + order.getUser().getId() + " не найден"));
            existingOrder.setUser(user);
        }
        return orderRepository.save(existingOrder);
    }
    
    @Override
    @Async
    public CompletableFuture<Order> updateOrderAsync(Long id, Order order) {
        return CompletableFuture.completedFuture(updateOrder(id, order));
    }
    
    @Override
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Заказ с id " + id + " не найден");
        }
        orderRepository.deleteById(id);
    }
    
    @Override
    @Async
    public CompletableFuture<Void> deleteOrderAsync(Long id) {
        deleteOrder(id);
        return CompletableFuture.completedFuture(null);
    }
}

