package com.example.Customer.Website.controllers;

import com.example.Customer.Website.models.SecurityModels.User;
import com.example.Customer.Website.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/update-user")
    public User updateUser(@RequestBody User userToUpdate) {
        return userService.updateUser(userToUpdate);
    }

    @GetMapping("/current-user")
    public User getUser(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return user.getUser();
    }
}
