package com.example.project2.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRequest {
    
    @NotBlank(message = "Имя пользователя не должно быть пустым")
    @Size(min = 3, max = 50, message = "Имя должно быть от 3 до 50 символов")
    private String username;
    
    @Email(message = "Некорректный email")
    @NotBlank(message = "Email не должен быть пустым")
    private String email;
    
    @NotBlank(message = "Пароль не должен быть пустым")
    @Size(min = 6, message = "Пароль должен содержать минимум 6 символов")
    private String password;
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
}

