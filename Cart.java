package com.example.hellofx;

import java.util.ArrayList;

public class Cart {
    private ArrayList<Product> items;
    private ArrayList<Integer> quantities;
    private float totalprice;

    // Constructor
    public Cart() {
        this.items = new ArrayList<>();
        this.quantities = new ArrayList<>();
        this.totalprice = 0;
    }

    public ArrayList<Product> getItems() {
        return items;
    }

    public void setItems(ArrayList<Product> items) {
        this.items = items;
    }

    public ArrayList<Integer> getQuantities() {
        return quantities;
    }

    public void setQuantities(ArrayList<Integer> quantities) {
        this.quantities = quantities;
    }

    public float getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(float totalprice) {
        this.totalprice = totalprice;
    }

    // Add a product to the cart
    public void addItem(Product product, int quantity) {
        if (items.contains(product)) {
            // Update quantity if the product is already in the cart
            int index = items.indexOf(product);
            quantities.set(index, quantities.get(index) + quantity);
        } else {
            // Add new product to the cart
            items.add(product);
            quantities.add(quantity);
        }
        // Update total price
        totalprice += product.getPrice() * quantity;
    }

    // View cart contents
    public void viewCart() {
        if (items.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }

        System.out.println("Cart Contents:");
        for (int i = 0; i < items.size(); i++) {
            Product product = items.get(i);
            int quantity = quantities.get(i);
            System.out.println(product.getName() + " x " + quantity + " - $"
                    + String.format("%.2f", product.getPrice())
                    + " each");
        }
        System.out.println("Total Price: $" + String.format("%.2f", totalprice));
    }

    // Calculate total price
    public float calculateTotal() {
        totalprice = 0;
        for (int i = 0; i < items.size(); i++) {
            totalprice += items.get(i).getPrice() * quantities.get(i);
        }
        return totalprice;
    }

    // Clear the cart
    public void clearCart() {
        items.clear();
        quantities.clear();
        totalprice = 0;
    }
    public void removeItem(Product product) {
        int index = items.indexOf(product);
        if (index >= 0) {
            items.remove(index);
            quantities.remove(index);
        }
    }

    // Check if the cart is empty
    public boolean isEmpty() {
        return items.isEmpty();
    }
}

