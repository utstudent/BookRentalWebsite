package com.example.Customer.Website.repositories;
import com.example.Customer.Website.models.SecurityModels.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByUsername(String username);

}
