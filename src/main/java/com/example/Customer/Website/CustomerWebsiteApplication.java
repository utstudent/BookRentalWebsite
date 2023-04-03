package com.example.Customer.Website;

import com.example.Customer.Website.models.Book;
import com.example.Customer.Website.models.Customer;
import com.example.Customer.Website.models.SecurityModels.Role;
import com.example.Customer.Website.models.SecurityModels.User;
import com.example.Customer.Website.repositories.RoleRepository;
import com.example.Customer.Website.services.BookService;
import com.example.Customer.Website.services.CustomerService;
import com.example.Customer.Website.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class CustomerWebsiteApplication implements CommandLineRunner {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // The main method is defined here which will start your application
    public static void main(String[] args) {
        SpringApplication.run(CustomerWebsiteApplication.class);
    }

    // You can also define a run method which performs an operation at runtime
    // In this example, the run method saves some Customer data into the database for testing
    @Override
    public void run(String... args) throws Exception {


        Role adminsUserRole = Role.builder().role(Role.Roles.ROLE_USER.name()).build();
        Role adminRole = Role.builder().role(Role.Roles.ROLE_ADMIN.name()).build();
        Role userRole = Role.builder().role(Role.Roles.ROLE_USER.name()).build();
//        if (roleRepository.findAll().isEmpty()) {
//            roleRepository.save(userRole);
//            roleRepository.save(adminRole);
//        }
//        userRole = roleRepository.getReferenceById(1L);
//        adminRole = roleRepository.getReferenceById(2L);
        List<Role> adminAuthorities = Arrays.asList(adminRole, adminsUserRole);

//
//        Role admin = roleRepository.findAll().stream().filter(role -> role.getRole() == Role.Roles.ROLE_ADMIN).findFirst().orElseThrow(() -> new IllegalStateException("What happened?"));

        if (userService.getAllUsers().isEmpty()) {
            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("password"))
                    .authorities(adminAuthorities)
                    .build();
            userService.saveUser(admin);
            User user = User.builder()
                    .username("user")
                    .password(passwordEncoder.encode("password"))
                    .authorities(List.of(userRole))
                    .build();
            userService.saveUser(user);
        }



        if (customerService.getAllCustomers().isEmpty()) {
            customerService.saveAllCustomer(Arrays.asList(
                            Customer.builder()
                                    .fullName("Customer 1")
                                    .emailAddress("customer1@gmail.com")
                                    .address("Customer Address One")
                                    .age(30)
                                    .build(),
                            Customer.builder().fullName("Customer 2").emailAddress("customer2@gmail.com").address("Customer Address Two").age(28).build(),
                            Customer.builder().fullName("Customer 3").emailAddress("customer3@gmail.com").address("Customer Address Three").age(32).build()
                    )
            );
        }

        if (bookService.getAllBooks().isEmpty()) {
            bookService.saveAllBooks(Arrays.asList(
                            Book.builder()
                                    .title("Harry Potter")
                                    .author("JK Rowling")
                                    .totalPages(230)
                                    .build(),
                            Book.builder()
                                    .title("Maze Runner")
                                    .author("karen bordough")
                                    .totalPages(305)
                                    .build(),
                            Book.builder()
                                    .title("Coding for beginners")
                                    .author("Matt Bryant")
                                    .totalPages(149)
                                    .build()
                    )
            );
        }

    }
}
