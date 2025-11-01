package com.example.project2.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class OrderRequest {
    
    @NotBlank(message = "Название заказа не должно быть пустым")
    private String title;
    
    @NotNull(message = "Цена не должна быть пустой")
    @DecimalMin(value = "0.0", message = "Цена должна быть положительной")
    private BigDecimal price;
    
    @NotNull(message = "ID пользователя обязателен")
    private Long userId;
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

