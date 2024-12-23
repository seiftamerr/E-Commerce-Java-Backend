package com.example.hellofx;

import java.util.ArrayList;

public class Database {
    // Static lists to store entities
    private static ArrayList<Customer> customers = new ArrayList<>();
    private static ArrayList<Category> categories = new ArrayList<>();
    private static ArrayList<Product> products = new ArrayList<>();
    private static ArrayList<Order> orders = new ArrayList<>();
    private static ArrayList<Cart> carts = new ArrayList<>();
    private static ArrayList<Admin> admins = new ArrayList<>();

    public static ArrayList<Customer> getCustomers() {
        return customers;
    }

    public static ArrayList<Product> getProducts() {
        return products;
    }

    public static ArrayList<Order> getOrders() {
        return orders;
    }

    public static ArrayList<Admin> getAdmins() {
        return admins;
    }

    public static ArrayList<Category> getCategories() {
        return categories;
    }

    public static void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public static void addAdmin(Admin admin) {
        admins.add(admin);
    }

    public static void addCategory(Category category) {
        categories.add(category);
    }

    public static void addProduct(Product product) {
        products.add(product);
    }

    public static void addOrder(Order order) {
        orders.add(order);
    }

    public static Category getCategoryById(int id) {
        for (Category category : categories) {
            if (category.getId() == id) {
                return category;
            }
        }
        return null;
    }

    public static Product getProductById(int productId) {
        for (Product product : products) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    public static boolean deleteProduct(int productId) {
        Product product = getProductById(productId);
        if (product != null) {
            products.remove(product);
            return true;
        }
        return false;
    }

    public static void initializeDummyData() {
        // Dummy Customers
        customers.add(new Customer("seif", "123", "1990-01-01", Gender.MALE, "Address 1", 900000, new ArrayList<>()));
        customers.add(new Customer("ali", "1234", "1990-01-02", Gender.MALE, "Address 2", 3333433, new ArrayList<>()));
        customers.add(new Customer("asaad", "789", "1990-01-01", Gender.MALE, "Address 1", 134313, new ArrayList<>()));
        customers.add(new Customer("adel", "6789", "1990-01-12", Gender.MALE, "Address 2", 2000000, new ArrayList<>()));
        // Dummy Categories
        Category electronics = new Category(1, "Electronics", "Devices and gadgets");
        Category books = new Category(2, "Books", "Reading materials");
        Category clothing = new Category(3, "Clothing", "Apparel and accessories");
        Category sports = new Category(4, "Sports", "Sporting goods and equipment");
        Database.addCategory(clothing);
        Database.addCategory(sports);
        Database.addCategory(electronics);
        Database.addCategory(books);

        // Dummy Products
        Product product1 = new Product(1, "Smartphone", 699.99, "Latest 5G smartphone", 50, electronics);
        Product product2 = new Product(2, "Laptop", 1199.99, "High-performance laptop", 30, electronics);
        Product product3 = new Product(3, "Fiction Book", 19.99, "Bestselling novel", 100, books);
        Product product4 = new Product(4, "T-Shirt", 19.99, "100% cotton T-shirt", 200, clothing);
        Product product5 = new Product(5, "Jeans", 49.99, "Slim-fit denim jeans", 150, clothing);
        Product product6 = new Product(6, "Football", 29.99, "Professional size football", 80, sports);
        Product product7 = new Product(7, "Tennis Racket", 89.99, "Lightweight graphite tennis racket", 40, sports);
        products.add(product4);
        products.add(product5);
        products.add(product6);
        products.add(product7);

        products.add(product1);
        products.add(product2);
        products.add(product3);

        // Dummy Admins
        admins.add(new Admin("admin1", "123", "1985-05-15", Gender.MALE, "Admin Office 1", "Manager", 40));
        admins.add(new Admin("admin2", "789", "1990-03-10", Gender.FEMALE, "Admin Office 2", "Supervisor", 35));
        admins.add(new Admin("admin3", "123", "1985-05-15", Gender.MALE, "Admin Office 1", "Manager", 40));
        admins.add(new Admin("admin4", "789", "1990-03-10", Gender.FEMALE, "Admin Office 2", "Supervisor", 35));

        // Dummy Carts
        Cart cart1 = new Cart();
        cart1.addItem(product1, 1); // 1 Smartphone
        cart1.addItem(product2, 2); // 2 Laptops
        carts.add(cart1);

        Cart cart2 = new Cart();
        cart2.addItem(product3, 3); // 3 Books
        carts.add(cart2);

        // Dummy Orders
        Order order1 = new Order(customers.get(0), PaymentMethod.CreditCard, cart1);
        Order order2 = new Order(customers.get(1), PaymentMethod.DebitCard, cart2);
        orders.add(order1);
        orders.add(order2);
    }
    public static void displayAllProducts(){
        System.out.println("\n--- List of Products ---");
        ArrayList<Product> products = Database.getProducts();
        if (products.isEmpty()) {
            System.out.println("No products found.");
        } else {
            for (Product product : products) {
                System.out.println("Product Name: " + product.getName());
                System.out.println("Product ID: "+ product.getId());
                System.out.println("Category: " + product.getCategory());
                System.out.println("Price: $" + String.format("%.2f", product.getPrice()));
                System.out.println("Stock: " + product.getStockQuantity() + " units");
                System.out.println("--------------------------");
            }
        }
    }
}

