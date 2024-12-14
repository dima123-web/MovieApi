package com.example.demo.controllers;

import com.example.demo.models.LoginRequest;
import com.example.demo.models.User;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173/")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        try {

            System.out.println(user);
            userService.registerUser(user);
            return ResponseEntity.ok("Регистрация успешна!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Ошибка: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        try {
            if (userService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword())) {
                return ResponseEntity.ok("Авторизация успешна!");
            } else {
                return ResponseEntity.status(401).body("Неверный email или пароль");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Ошибка: " + e.getMessage());
        }
    }
}
