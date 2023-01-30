package com.example.Customer.Website.exceptions;

public class NoSuchCustomerException extends Exception {

    public NoSuchCustomerException(String message) {
        super(message);
    }

    public NoSuchCustomerException(){}
}
