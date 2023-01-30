package com.example.Customer.Website.services;

import com.example.Customer.Website.models.Book;

import java.util.List;

public interface BookService {

    List<Book> getAllBooks();

    Book saveBook(Book book);

    Book getBook(Long id);

    void deleteBook(Long id);

    List<Book> saveAllBooks(List<Book> bookList);
}
