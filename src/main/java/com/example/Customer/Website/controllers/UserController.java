package com.example.Customer.Website.controllers;

import com.example.Customer.Website.models.Book;
import com.example.Customer.Website.models.SecurityModels.Role;
import com.example.Customer.Website.models.SecurityModels.User;
import com.example.Customer.Website.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Controller
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/current-user")
    public User getUser(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return user;
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping(value = "/register")
    // As the Model is received back from the view, @ModelAttribute
    // creates a Book based on the object you collected from the HTML page
    public String saveUser(@ModelAttribute("user") User user) {
        //User newuser = new User(user.getUsername(), passwordEncoder.encode(user.getPassword()), user.getAuthorities());
        User newuser1 = User.builder().username(user.getUsername()).password(passwordEncoder.encode(user.getPassword())).authorities(Arrays.asList(Role.builder().role(Role.Roles.ROLE_USER).build())).build();
        userService.saveUser(newuser1);
        return "redirect:/";
    }
}
