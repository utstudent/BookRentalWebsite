package com.example.Customer.Website.controllers;

import com.example.Customer.Website.models.Book;
import com.example.Customer.Website.models.Customer;
import com.example.Customer.Website.services.BookServiceImpl;
import com.example.Customer.Website.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookServiceImpl bookService;

    @Autowired
    CustomerService customerService;

    @GetMapping
    public String viewHomePage(Model model) {
        // Here you call the service to retrieve all the books
        final List<Book> bookList = bookService.getAllBooks();
        // Once the book are retrieved, you can store them in model and return it to the view
        model.addAttribute("bookList", bookList);
        return "books-home";
    }

    @GetMapping("/new")
    public String showNewBookPage(Model model) {
        // Here a new (empty) Book is created and then sent to the view
        Book book = new Book();
        model.addAttribute("book", book);
        return "new-book";
    }

    @GetMapping("/assign/{id}")
    public String assignBook(@PathVariable(name = "id") Long id, Model model) {
        Customer customer = customerService.getCustomer(id);
        List<Book> bookList = bookService.getAvailableBooks();
        model.addAttribute("customer", customer);
        model.addAttribute("bookList", bookList);
        return "assign-book";
    }

    @PostMapping("/assign")
    public String saveBookAssignment(@RequestParam("customerId") Long customerId, @RequestParam("bookId") Long bookId) {
        Book book = bookService.getBook(bookId);
        book.setCustomer(customerService.getCustomer(customerId));
        bookService.saveBook(book);
        return "redirect:/";

    }

    @PostMapping(value = "/save")
    // As the Model is received back from the view, @ModelAttribute
    // creates a Book based on the object you collected from the HTML page
    public String saveBook(@ModelAttribute("book") Book book) {
        bookService.saveBook(book);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String showEditBookPage(@PathVariable(name = "id") Long id, Model model) {
        Book book = bookService.getBook(id);
        model.addAttribute("book", book);
        return "edit-book";
    }

    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable(name = "id") Long id, @ModelAttribute("book") Book book, Model model) {
        if (!id.equals(book.getId())) {
            model.addAttribute("message",
                    "Cannot update, book id " + book.getId()
                            + " doesn't match id to be updated: " + id + ".");
            return "error-page";
        }
        bookService.saveBook(book);
        return "redirect:/book";
    }

    @RequestMapping("/delete/{id}")
    public String deleteBook(@PathVariable(name = "id") Long id) {
        bookService.deleteBook(id);
        return "redirect:/book";
    }

    @RequestMapping("/remove/{id}")
    public String removeBook(@PathVariable(name = "id") Long bookId) {
        Book book = bookService.getBook(bookId);
        book.setCustomer(null);
        bookService.saveBook(book);
        return "redirect:/";
    }

}
