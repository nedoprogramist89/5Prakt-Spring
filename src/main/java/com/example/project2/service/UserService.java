package com.example.project2.service;

import com.example.project2.model.User;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface UserService {
    List<User> findAllUsers();
    CompletableFuture<List<User>> findAllUsersAsync();
    User findUserById(Long id);
    CompletableFuture<User> findUserByIdAsync(Long id);
    User createUser(User user);
    CompletableFuture<User> createUserAsync(User user);
    User updateUser(Long id, User user);
    CompletableFuture<User> updateUserAsync(Long id, User user);
    void deleteUser(Long id);
    CompletableFuture<Void> deleteUserAsync(Long id);
    User findByUsername(String username);
}

