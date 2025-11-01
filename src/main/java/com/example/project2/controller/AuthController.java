package com.example.project2.controller;

import com.example.project2.model.User;
import com.example.project2.repository.UserRepository;
import com.example.project2.security.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication Controller", description = "API для аутентификации и регистрации")
public class AuthController {
    
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public AuthController(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @PostMapping("/register")
    @Operation(summary = "Регистрация пользователя", description = "Регистрирует нового пользователя и возвращает JWT токен")
    public ResponseEntity<Map<String, String>> register(@Valid @RequestBody User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Пользователь с таким именем уже существует");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Пользователь с таким email уже существует");
        }
        
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        
        String jwt = jwtService.generateToken(savedUser.getUsername());
        
        Map<String, String> response = new HashMap<>();
        response.put("token", jwt);
        response.put("message", "Регистрация прошла успешно");
        response.put("username", savedUser.getUsername());
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/login")
    @Operation(summary = "Вход пользователя", description = "Аутентифицирует пользователя и возвращает JWT токен")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");
        
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        
        String jwt = jwtService.generateToken(username);
        
        Map<String, String> response = new HashMap<>();
        response.put("token", jwt);
        response.put("message", "Вход выполнен успешно");
        response.put("username", username);
        
        return ResponseEntity.ok(response);
    }
}

