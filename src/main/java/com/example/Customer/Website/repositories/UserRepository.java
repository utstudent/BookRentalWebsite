package com.example.Customer.Website.repositories;
import com.example.Customer.Website.models.SecurityModels.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long>{

    User findByUsername(String username);

}
