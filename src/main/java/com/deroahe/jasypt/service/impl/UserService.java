package com.deroahe.jasypt.service.impl;

import com.deroahe.jasypt.model.User;
import com.deroahe.jasypt.repository.UserRepository;
import com.deroahe.jasypt.service.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    @Resource(name = "encryptionService")
    private EncryptionService encryptionService;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        user.setPassword(encryptionService.encrypt(user.getPassword()));
        return userRepository.save(user);
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    public void deleteUserById(String id) {
        userRepository.deleteById(id);
    }

    public Optional<User> findUserById(String id) {
        return userRepository.findById(id);
    }
}
