package com.example.Customer.Website.repositories;

import com.example.Customer.Website.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    Book findByCustomerId(Long id);
}