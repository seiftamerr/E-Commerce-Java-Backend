package com.example.hellofx;

import java.util.ArrayList;

public class Order {
    private Customer customer;
    private PaymentMethod paymentMethod;
    private Cart cart;

    public Order(Customer customer, PaymentMethod paymentMethod, Cart cart) {
        this.customer = customer;
        this.paymentMethod = paymentMethod;
        this.cart = cart;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }


    public String getReceipt() {
        StringBuilder receipt = new StringBuilder();
        receipt.append("----- Order Receipt -----\n");
        receipt.append("Customer: ").append(customer.getUsername()).append("\n");
        receipt.append("Payment Method: ").append(paymentMethod).append("\n");
        receipt.append("Items:\n");

        for (int i = 0; i < cart.getItems().size(); i++) {
            Product product = cart.getItems().get(i);
            int quantity = cart.getQuantities().get(i);
            receipt.append("- ").append(product.getName())
                    .append(" x ").append(quantity)
                    .append(" ($").append(String.format("%.2f", product.getPrice())).append(" each)\n");
        }

        receipt.append("-------------------------\n");
        receipt.append("Total Price: $").append(String.format("%.2f", cart.calculateTotal())).append("\n");
        receipt.append("-------------------------\n");

        return receipt.toString();
    }
}

