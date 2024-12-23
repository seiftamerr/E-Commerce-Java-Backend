package com.example.hellofx;

import java.util.List;
import java.util.ArrayList;

public class Customer extends User {
    private double balance;
    private List<String> interests;


    // Simulate a database for registered customers
    public Customer(){}
    public Customer(String username, String password, String dateOfBirth, Gender gender, String address, double balance, List<String> interests) {
        super(username, password, dateOfBirth, gender, address);
        this.balance = balance;
        this.interests = interests;
    }

    // Getters and Setters
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }

    // Login Implementation
    @Override
    public Customer Login(String username, String password) {
        for (Customer customer : Database.getCustomers()) {
            if (customer.getUsername().equals(username) && customer.getPassword().equals(password)) {
                System.out.println("Login successful for: " + username);
                return customer;
            }
        }
        System.out.println("Login failed. Incorrect username or password.");
        return null;
    }

    // Registration Implementation
    @Override
    public void Registration(String username, String password, String dateOfBirth, Gender gender, String address) {
        // Check if the username is already taken
        for (Customer customer : Database.getCustomers()) {
            if (customer.getUsername().equals(username)) {
                System.out.println("Registration failed. Username already exists.");
                return;
            }
        }

        // Create a new customer and add it to the registeredCustomers list
        Customer newCustomer = new Customer(username, password, dateOfBirth, gender, address, 0.0, new ArrayList<>());
        Database.addCustomer(newCustomer);
        System.out.println("Registration successful for: " + username);
    }

    @Override
    public String toString() {
        return super.toString()+ "Customer{" +
                "balance=" + balance +
                ", interests=" + interests +
                '}';
    }
}
