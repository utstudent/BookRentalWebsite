package com.example.Customer.Website.repositories;

import com.example.Customer.Website.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}