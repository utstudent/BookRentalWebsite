package com.example.Customer.Website.services;

import com.example.Customer.Website.models.Book;
import com.example.Customer.Website.models.Customer;
import com.example.Customer.Website.models.SecurityModels.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User saveUser(User user);

    User getUser(Long id);

    void deleteUser(Long id);

    List<User> saveAllUsers(List<User> userList);

    User updateUser(User userToUpdate);
}
