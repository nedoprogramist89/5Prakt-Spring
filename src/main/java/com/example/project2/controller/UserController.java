package com.example.project2.controller;

import com.example.project2.model.User;
import com.example.project2.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Controller", description = "API для управления пользователями")
public class UserController {
    
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping
    @Operation(summary = "Получить всех пользователей", description = "Возвращает список всех пользователей")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }
    
    @GetMapping("/async")
    @Operation(summary = "Получить всех пользователей асинхронно", description = "Асинхронно возвращает список всех пользователей")
    public CompletableFuture<ResponseEntity<List<User>>> getAllUsersAsync() {
        return userService.findAllUsersAsync()
                .thenApply(ResponseEntity::ok);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Получить пользователя по ID", description = "Возвращает пользователя с указанным ID")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }
    
    @GetMapping("/{id}/async")
    @Operation(summary = "Получить пользователя по ID асинхронно", description = "Асинхронно возвращает пользователя с указанным ID")
    public CompletableFuture<ResponseEntity<User>> getUserByIdAsync(@PathVariable Long id) {
        return userService.findUserByIdAsync(id)
                .thenApply(ResponseEntity::ok);
    }
    
    @PostMapping
    @Operation(summary = "Создать пользователя", description = "Создает нового пользователя")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.createUser(user));
    }
    
    @PostMapping("/async")
    @Operation(summary = "Создать пользователя асинхронно", description = "Асинхронно создает нового пользователя")
    public CompletableFuture<ResponseEntity<User>> createUserAsync(@Valid @RequestBody User user) {
        return userService.createUserAsync(user)
                .thenApply(u -> ResponseEntity.status(HttpStatus.CREATED).body(u));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Обновить пользователя", description = "Обновляет информацию о пользователе")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }
    
    @PutMapping("/{id}/async")
    @Operation(summary = "Обновить пользователя асинхронно", description = "Асинхронно обновляет информацию о пользователе")
    public CompletableFuture<ResponseEntity<User>> updateUserAsync(@PathVariable Long id, @Valid @RequestBody User user) {
        return userService.updateUserAsync(id, user)
                .thenApply(ResponseEntity::ok);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить пользователя", description = "Удаляет пользователя по ID")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/{id}/async")
    @Operation(summary = "Удалить пользователя асинхронно", description = "Асинхронно удаляет пользователя по ID")
    public CompletableFuture<ResponseEntity<Void>> deleteUserAsync(@PathVariable Long id) {
        return userService.deleteUserAsync(id)
                .thenApply(v -> ResponseEntity.noContent().build());
    }
}

