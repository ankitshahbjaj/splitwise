package com.setu.splitwise.controller;


import com.setu.splitwise.exchange.businessObject.UserBO;
import com.setu.splitwise.exchange.request.CreateUserRequest;
import com.setu.splitwise.service.ExpenseService;
import com.setu.splitwise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
public class UserController {
    private final UserService userService;
    private final ExpenseService expenseService;

    @Autowired
    public UserController(UserService userService,
                          ExpenseService expenseService) {
        this.userService = userService;
        this.expenseService = expenseService;
    }

    @PostMapping("/users")
    public UserBO createUser(@Valid @RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }

    @GetMapping("/users/id/{id}")
    public UserBO getUserByPhoneNumber(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/users/phone/{phoneNumber}")
    public UserBO getUserByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber) {
        return userService.getUserByPhoneNumber(phoneNumber);
    }
}
