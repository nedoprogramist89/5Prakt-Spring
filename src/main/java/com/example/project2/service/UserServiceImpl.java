package com.example.project2.service;

import com.example.project2.exception.ResourceNotFoundException;
import com.example.project2.model.User;
import com.example.project2.repository.UserRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
    
    @Override
    @Async
    @Transactional(readOnly = true)
    public CompletableFuture<List<User>> findAllUsersAsync() {
        return CompletableFuture.completedFuture(userRepository.findAll());
    }
    
    @Override
    @Transactional(readOnly = true)
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь с id " + id + " не найден"));
    }
    
    @Override
    @Async
    @Transactional(readOnly = true)
    public CompletableFuture<User> findUserByIdAsync(Long id) {
        return CompletableFuture.completedFuture(findUserById(id));
    }
    
    @Override
    public User createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Пользователь с таким именем уже существует");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Пользователь с таким email уже существует");
        }
        return userRepository.save(user);
    }
    
    @Override
    @Async
    public CompletableFuture<User> createUserAsync(User user) {
        return CompletableFuture.completedFuture(createUser(user));
    }
    
    @Override
    public User updateUser(Long id, User user) {
        User existingUser = findUserById(id);
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        return userRepository.save(existingUser);
    }
    
    @Override
    @Async
    public CompletableFuture<User> updateUserAsync(Long id, User user) {
        return CompletableFuture.completedFuture(updateUser(id, user));
    }
    
    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Пользователь с id " + id + " не найден");
        }
        userRepository.deleteById(id);
    }
    
    @Override
    @Async
    public CompletableFuture<Void> deleteUserAsync(Long id) {
        deleteUser(id);
        return CompletableFuture.completedFuture(null);
    }
    
    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь с именем " + username + " не найден"));
    }
}

