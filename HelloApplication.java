package com.example.hellofx;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HelloApplication extends Application {

    private Scene mainScene; // Role Selection Scene
    private Scene adminScene; // Admin Dashboard
    private Scene customerScene; // Customer Dashboard

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("E-Commerce System");
        Database.initializeDummyData();

        mainScene = createRoleSelectionScreen(primaryStage);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    private Scene createRoleSelectionScreen(Stage primaryStage) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        Label prompt = new Label("Select your role:");
        Button adminLoginButton = new Button("Admin Login");
        Button customerLoginButton = new Button("Customer Login");
        Button registerButton = new Button("Register");

        adminLoginButton.setOnAction(e -> primaryStage.setScene(createLoginScreen(primaryStage, true)));
        customerLoginButton.setOnAction(e -> primaryStage.setScene(createLoginScreen(primaryStage, false)));
        registerButton.setOnAction(e -> primaryStage.setScene(createRegistrationScreen(primaryStage)));

        layout.getChildren().addAll(prompt, adminLoginButton, customerLoginButton, registerButton);
        return new Scene(layout, 400, 200);
    }

    private Scene createLoginScreen(Stage primaryStage, boolean isAdmin) {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");
        Button backButton = new Button("Back");

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (isAdmin) {
                Admin admin = Database.getAdmins().stream()
                        .filter(a -> a.getUsername().equals(username) && a.getPassword().equals(password))
                        .findFirst()
                        .orElse(null);

                if (admin != null) {
                    adminScene = createAdminDashboard(primaryStage, admin);
                    primaryStage.setScene(adminScene);
                } else {
                    showAlert("Invalid Admin credentials!");
                }
            } else {
                Customer customer = Database.getCustomers().stream()
                        .filter(c -> c.getUsername().equals(username) && c.getPassword().equals(password))
                        .findFirst()
                        .orElse(null);

                if (customer != null) {
                    customerScene = createCustomerDashboard(primaryStage, customer);
                    primaryStage.setScene(customerScene);
                } else {
                    showAlert("Invalid Customer credentials!");
                }
            }
        });

        backButton.setOnAction(e -> primaryStage.setScene(mainScene));

        grid.add(usernameLabel, 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);
        grid.add(loginButton, 0, 2);
        grid.add(backButton, 1, 2);

        return new Scene(grid, 400, 200);
    }

    private Scene createRegistrationScreen(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Label dobLabel = new Label("Date of Birth:");
        TextField dobField = new TextField();
        Label genderLabel = new Label("Gender:");
        ComboBox<String> genderComboBox = new ComboBox<>();
        genderComboBox.getItems().addAll("Male", "Female");
        Label addressLabel = new Label("Address:");
        TextField addressField = new TextField();
        Button registerButton = new Button("Register");
        Button backButton = new Button("Back");

        registerButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String dob = dobField.getText();
            String genderInput = genderComboBox.getValue();
            String address = addressField.getText();

            if (username.isEmpty() || password.isEmpty() || dob.isEmpty() || genderInput == null || address.isEmpty()) {
                showAlert("All fields are required!");
                return;
            }

            Gender gender = Gender.valueOf(genderInput.toUpperCase());
            Customer customer = new Customer(username, password, dob, gender, address, 0.0, new ArrayList<>());
            Database.addCustomer(customer);
            showAlert("Registration successful!");
            primaryStage.setScene(mainScene);
        });

        backButton.setOnAction(e -> primaryStage.setScene(mainScene));

        grid.add(usernameLabel, 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);
        grid.add(dobLabel, 0, 2);
        grid.add(dobField, 1, 2);
        grid.add(genderLabel, 0, 3);
        grid.add(genderComboBox, 1, 3);
        grid.add(addressLabel, 0, 4);
        grid.add(addressField, 1, 4);
        grid.add(registerButton, 0, 5);
        grid.add(backButton, 1, 5);

        return new Scene(grid, 400, 300);
    }

    private Scene createAdminDashboard(Stage primaryStage, Admin admin) {
        // Layout setup
        VBox layout = new VBox(15); // Increased spacing for better readability
        layout.setPadding(new Insets(20));

        // UI Components
        Label welcomeLabel = new Label("Welcome, Admin: " + admin.getUsername());
        welcomeLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Button viewCustomersButton = new Button("View Customers");
        Button viewProductsButton = new Button("View Products");
        Button viewOrdersButton = new Button("View Orders");
        Button createProductButton = new Button("Create Product");
        Button renameProductButton = new Button("Rename Product");
        Button updateProductButton = new Button("Update Product");
        Button deleteProductButton = new Button("Delete Product");
        Button logoutButton = new Button("Logout");

        // View Customers Action
        viewCustomersButton.setOnAction(e ->primaryStage.setScene(createCustomerListScene(primaryStage)));

        // View Products Action
        viewProductsButton.setOnAction(e -> primaryStage.setScene(createProductListScene(primaryStage)));

        // View Orders Action
        viewOrdersButton.setOnAction(e -> primaryStage.setScene(createOrderListScene(primaryStage)));

        // Create Product Action
        createProductButton.setOnAction(e -> primaryStage.setScene(createAddProductScreen(primaryStage,admin)));
        renameProductButton.setOnAction(e-> primaryStage.setScene(createRenameProductScreen(primaryStage,admin)));
        updateProductButton.setOnAction(e->primaryStage.setScene(createUpdateProductScreen(primaryStage,admin)));
        deleteProductButton.setOnAction(e->primaryStage.setScene(createDeleteProductScreen(primaryStage,admin)));
        // Logout Action
        logoutButton.setOnAction(e -> primaryStage.setScene(mainScene));

        // Add components to layout
        layout.getChildren().addAll(
                welcomeLabel,
                viewCustomersButton,
                viewProductsButton,
                viewOrdersButton,
                createProductButton,
                renameProductButton,
                updateProductButton,
                deleteProductButton,
                logoutButton
        );

        // Return the scene
        return new Scene(layout, 500, 400);
    }


    private Scene createCustomerDashboard(Stage primaryStage, Customer customer) {
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));

        Label welcomeLabel = new Label("Welcome, " + customer.getUsername());
        welcomeLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Button viewProductsButton = new Button("View Products");
        Button addToCartButton = new Button("Add Product to Cart");
        Button viewCartButton = new Button("View Cart");
        Button placeButton = new Button("Place order");
        Button logoutButton = new Button("Logout");

        // Customer's Cart
        Cart customerCart = new Cart();

        // View Products Action
        viewProductsButton.setOnAction(e -> primaryStage.setScene(createProductListSceneCustomer(primaryStage)));

        // Add Product to Cart Action
        addToCartButton.setOnAction(e -> showAddToCartForm(customerCart));
        viewCartButton.setOnAction(e->primaryStage.setScene(createCartViewScene(primaryStage,customerCart)));
        placeButton.setOnAction(e-> primaryStage.setScene(createPlaceOrderScene(primaryStage,customerCart,customer)));
        // Logout Action
        logoutButton.setOnAction(e -> primaryStage.setScene(mainScene));

        layout.getChildren().addAll(
                welcomeLabel,
                viewProductsButton,
                addToCartButton,
                viewCartButton,
                placeButton,
                logoutButton
        );

        return new Scene(layout,500,400);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private Scene createAddProductScreen(Stage primaryStage, Admin admin) {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        // Input Fields
        Label productIdLabel = new Label("Product ID:");
        TextField productIdField = new TextField();

        Label productNameLabel = new Label("Product Name:");
        TextField productNameField = new TextField();

        Label productPriceLabel = new Label("Product Price:");
        TextField productPriceField = new TextField();

        Label productDescriptionLabel = new Label("Description:");
        TextField productDescriptionField = new TextField();

        Label stockQuantityLabel = new Label("Stock Quantity:");
        TextField stockQuantityField = new TextField();

        Label categoryLabel = new Label("Category:");
        ComboBox<String> categoryComboBox = new ComboBox<>();
        for (Category category : Database.getCategories()) {
            categoryComboBox.getItems().add(category.getName() + " (ID: " + category.getId() + ")");
        }

        // Buttons
        Button submitButton = new Button("Submit");
        Button cancelButton = new Button("Cancel");

        submitButton.setOnAction(e -> {
            try {
                int productId = Integer.parseInt(productIdField.getText());
                String productName = productNameField.getText();
                double productPrice = Double.parseDouble(productPriceField.getText());
                String productDescription = productDescriptionField.getText();
                int stockQuantity = Integer.parseInt(stockQuantityField.getText());

                // Validate category selection
                String selectedCategory = categoryComboBox.getValue();
                if (selectedCategory == null) {
                    showAlert("Please select a category.");
                    return;
                }

                // Extract category ID
                int categoryId = Integer.parseInt(selectedCategory.replaceAll("\\D+", ""));
                Category category = Database.getCategoryById(categoryId);

                // Validate inputs
                if (productName.isEmpty() || productDescription.isEmpty() || category == null) {
                    showAlert("All fields must be filled in correctly!");
                    return;
                }

                // Add product to database
                Product newProduct = new Product(productId, productName, productPrice, productDescription, stockQuantity, category);
                Database.addProduct(newProduct);
                showAlert("Product added successfully!");
                primaryStage.setScene(createAdminDashboard(primaryStage, admin)); // Pass admin here
            } catch (NumberFormatException ex) {
                showAlert("Invalid input! Please check numeric fields.");
            } catch (Exception ex) {
                showAlert("An unexpected error occurred: " + ex.getMessage());
            }
        });

        cancelButton.setOnAction(e -> primaryStage.setScene(createAdminDashboard(primaryStage, admin))); // Pass admin here

        // Add Components to Layout
        grid.add(productIdLabel, 0, 0);
        grid.add(productIdField, 1, 0);
        grid.add(productNameLabel, 0, 1);
        grid.add(productNameField, 1, 1);
        grid.add(productPriceLabel, 0, 2);
        grid.add(productPriceField, 1, 2);
        grid.add(productDescriptionLabel, 0, 3);
        grid.add(productDescriptionField, 1, 3);
        grid.add(stockQuantityLabel, 0, 4);
        grid.add(stockQuantityField, 1, 4);
        grid.add(categoryLabel, 0, 5);
        grid.add(categoryComboBox, 1, 5);
        grid.add(submitButton, 0, 6);
        grid.add(cancelButton, 1, 6);

        return new Scene(grid,500,400);
    }
    private Scene createRenameProductScreen(Stage primaryStage, Admin admin) {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        // Labels and Input Fields
        Label productLabel = new Label("Select Product:");
        ComboBox<String> productComboBox = new ComboBox<>();
        productComboBox.setPromptText("Select a product");

        // Map to associate product names with Product objects
        Map<String, Product> productMap = new HashMap<>();
        for (Product product : Database.getProducts()) {
            productMap.put(product.getName(), product); // Map product name to Product object
            productComboBox.getItems().add(product.getName()); // Add only product names to ComboBox
        }

        Label newProductNameLabel = new Label("New Product Name:");
        TextField newProductNameField = new TextField();

        Button submitButton = new Button("Submit");
        Button cancelButton = new Button("Cancel");

        // Submit Button Action
        submitButton.setOnAction(e -> {
            try {
                String selectedProductName = productComboBox.getValue();
                String newProductName = newProductNameField.getText();

                if (selectedProductName == null) {
                    showAlert("Please select a product.");
                } else if (newProductName.isEmpty()) {
                    showAlert("New product name cannot be empty!");
                } else {
                    Product selectedProduct = productMap.get(selectedProductName);
                    selectedProduct.setName(newProductName); // Rename the product
                    showAlert("Product renamed successfully!");
                    primaryStage.setScene(createAdminDashboard(primaryStage, admin)); // Go back to Admin Dashboard
                }
            } catch (Exception ex) {
                showAlert("An error occurred: " + ex.getMessage());
            }
        });

        // Cancel Button Action
        cancelButton.setOnAction(e -> primaryStage.setScene(createAdminDashboard(primaryStage, admin)));

        // Add Components to Grid
        grid.add(productLabel, 0, 0);
        grid.add(productComboBox, 1, 0);
        grid.add(newProductNameLabel, 0, 1);
        grid.add(newProductNameField, 1, 1);
        grid.add(submitButton, 0, 2);
        grid.add(cancelButton, 1, 2);

        return new Scene(grid,400,200);
    }


    private Scene createUpdateProductScreen(Stage primaryStage, Admin admin) {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        // Labels and Input Fields
        Label productLabel = new Label("Select Product:");
        ComboBox<Product> productComboBox = new ComboBox<>();
        productComboBox.setPromptText("Select a product");

        // Populate ComboBox with formatted product details
        for (Product product : Database.getProducts()) {
            productComboBox.getItems().add(product);
        }

        Label newPriceLabel = new Label("New Price (Enter -1 to skip):");
        TextField newPriceField = new TextField();

        Label newStockLabel = new Label("New Stock Quantity (Enter -1 to skip):");
        TextField newStockField = new TextField();

        Button submitButton = new Button("Submit");
        Button cancelButton = new Button("Cancel");

        // Customize how ComboBox displays Product objects
        productComboBox.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Product item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName() + " (Price: $" + item.getPrice() + ", Stock: " + item.getStockQuantity() + ")");
                }
            }
        });
        productComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Product item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName() + " (Price: $" + item.getPrice() + ", Stock: " + item.getStockQuantity() + ")");
                }
            }
        });

        // Submit Button Action
        submitButton.setOnAction(e -> {
            try {
                Product selectedProduct = productComboBox.getValue();

                if (selectedProduct == null) {
                    showAlert("Please select a product.");
                    return;
                }

                // Update price if entered
                String newPriceInput = newPriceField.getText();
                if (!newPriceInput.isEmpty()) {
                    double newPrice = Double.parseDouble(newPriceInput);
                    if (newPrice >= 0) {
                        selectedProduct.setPrice(newPrice);
                    }
                }

                // Update stock quantity if entered
                String newStockInput = newStockField.getText();
                if (!newStockInput.isEmpty()) {
                    int newStock = Integer.parseInt(newStockInput);
                    if (newStock >= 0) {
                        selectedProduct.setStockQuantity(newStock);
                    }
                }

                showAlert("Product updated successfully!");
                primaryStage.setScene(createAdminDashboard(primaryStage, admin));
            } catch (NumberFormatException ex) {
                showAlert("Invalid input! Please enter valid numeric values.");
            } catch (Exception ex) {
                showAlert("An unexpected error occurred: " + ex.getMessage());
            }
        });

        // Cancel Button Action
        cancelButton.setOnAction(e -> primaryStage.setScene(createAdminDashboard(primaryStage, admin)));

        // Add Components to Grid
        grid.add(productLabel, 0, 0);
        grid.add(productComboBox, 1, 0);
        grid.add(newPriceLabel, 0, 1);
        grid.add(newPriceField, 1, 1);
        grid.add(newStockLabel, 0, 2);
        grid.add(newStockField, 1, 2);
        grid.add(submitButton, 0, 3);
        grid.add(cancelButton, 1, 3);

        return new Scene(grid,600,300);
    }

    private Scene createDeleteProductScreen(Stage primaryStage, Admin admin) {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        // Labels and Input Fields
        Label productLabel = new Label("Select Product:");
        ComboBox<Product> productComboBox = new ComboBox<>();
        productComboBox.setPromptText("Select a product");

        // Populate ComboBox with product details
        for (Product product : Database.getProducts()) {
            productComboBox.getItems().add(product);
        }

        Button deleteButton = new Button("Delete");
        Button cancelButton = new Button("Cancel");

        // Customize how ComboBox displays Product objects
        productComboBox.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Product item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName() + " (Price: $" + item.getPrice() + ")");
                }
            }
        });
        productComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Product item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName() + " (Price: $" + item.getPrice() + ")");
                }
            }
        });

        // Delete Button Action
        deleteButton.setOnAction(e -> {
            try {
                Product selectedProduct = productComboBox.getValue();

                if (selectedProduct == null) {
                    showAlert("Please select a product to delete.");
                    return;
                }

                boolean removed = Database.deleteProduct(selectedProduct.getId());

                if (removed) {
                    showAlert("Product deleted successfully!");
                } else {
                    showAlert("An error occurred while deleting the product.");
                }

                // Refresh the screen after deletion
                primaryStage.setScene(createAdminDashboard(primaryStage, admin));
            } catch (Exception ex) {
                showAlert("An unexpected error occurred: " + ex.getMessage());
            }
        });

        // Cancel Button Action
        cancelButton.setOnAction(e -> primaryStage.setScene(createAdminDashboard(primaryStage, admin)));

        // Add Components to Grid
        grid.add(productLabel, 0, 0);
        grid.add(productComboBox, 1, 0);
        grid.add(deleteButton, 0, 1);
        grid.add(cancelButton, 1, 1);

        return new Scene(grid,500,200);
    }

    private Scene createCustomerListScene(Stage primaryStage) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        Label title = new Label("Customer List");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        ListView<String> customerListView = new ListView<>();
        for (Customer customer : Database.getCustomers()) {
            customerListView.getItems().add("Username: " + customer.getUsername() +
                    " | Gender: " + customer.getGender() + // Add gender here
                    " | Address: " + customer.getAddress() +
                    " | Balance: $" + String.format("%.2f", customer.getBalance()));
        }

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> primaryStage.setScene(adminScene));

        layout.getChildren().addAll(title, customerListView, backButton);
        return new Scene(layout, 500, 400);
    }
    private Scene createProductListScene(Stage primaryStage) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        Label title = new Label("Product List");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        ListView<String> productListView = new ListView<>();
        for (Product product : Database.getProducts()) {
            productListView.getItems().add("Product: " + product.getName() +
                    " | Price: $" + String.format("%.2f", product.getPrice()) +
                    " | Stock: " + product.getStockQuantity());
        }

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> primaryStage.setScene(adminScene));

        layout.getChildren().addAll(title, productListView, backButton);
        return new Scene(layout,500,400);
    }
    private Scene createOrderListScene(Stage primaryStage) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        Label title = new Label("Order List");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        ListView<String> orderListView = new ListView<>();
        int count = 0;
        for (Order order : Database.getOrders()) {
            count++;
            orderListView.getItems().add("Order #" + count +
                    " | Customer: " + order.getCustomer().getUsername() +
                    "\nItems: " + formatCartItems(order.getCart()) +
                    "\nTotal Price: $" + String.format("%.2f", order.getCart().calculateTotal()) +
                    " | Payment: " + order.getPaymentMethod());
        }

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> primaryStage.setScene(adminScene));

        layout.getChildren().addAll(title, orderListView, backButton);
        return new Scene(layout, 500, 400);
    }

    // Helper to format cart items
    private String formatCartItems(Cart cart) {
        StringBuilder items = new StringBuilder();
        for (int i = 0; i < cart.getItems().size(); i++) {
            Product product = cart.getItems().get(i);
            int quantity = cart.getQuantities().get(i);
            items.append(product.getName()).append(" x ").append(quantity).append(", ");
        }
        return items.toString();
    }
    private void showAddToCartForm(Cart customerCart) {
        Stage addToCartStage = new Stage();
        addToCartStage.setTitle("Add Product to Cart");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        // Product Selection ComboBox
        Label productLabel = new Label("Select Product:");
        ComboBox<Product> productComboBox = new ComboBox<>();
        productComboBox.getItems().addAll(Database.getProducts());
        productComboBox.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Product product, boolean empty) {
                super.updateItem(product, empty);
                if (empty || product == null) {
                    setText(null);
                } else {
                    setText(product.getName() + " - $" + product.getPrice());
                }
            }
        });
        productComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Product product, boolean empty) {
                super.updateItem(product, empty);
                if (empty || product == null) {
                    setText(null);
                } else {
                    setText(product.getName() + " - $" + product.getPrice());
                }
            }
        });

        // Quantity Input
        Label quantityLabel = new Label("Enter Quantity:");
        TextField quantityField = new TextField();

        // Buttons
        Button addButton = new Button("Add to Cart");
        Button cancelButton = new Button("Cancel");

        // Add Product Action
        addButton.setOnAction(e -> {
            Product selectedProduct = productComboBox.getValue();
            if (selectedProduct == null) {
                showAlert("Please select a product.");
                return;
            }

            try {
                int quantity = Integer.parseInt(quantityField.getText());
                if (quantity <= 0) {
                    showAlert("Quantity must be greater than zero.");
                    return;
                }

                if (quantity > selectedProduct.getStockQuantity()) {
                    showAlert("Insufficient stock available.");
                    return;
                }

                // Add the product to the cart
                customerCart.addItem(selectedProduct, quantity);
                selectedProduct.setStockQuantity(selectedProduct.getStockQuantity() - quantity); // Deduct stock
                showAlert("Product added to cart!");
                addToCartStage.close();
            } catch (NumberFormatException ex) {
                showAlert("Invalid quantity. Please enter a number.");
            }
        });

        // Cancel Action
        cancelButton.setOnAction(e -> addToCartStage.close());

        // Add Components to Grid
        grid.add(productLabel, 0, 0);
        grid.add(productComboBox, 1, 0);
        grid.add(quantityLabel, 0, 1);
        grid.add(quantityField, 1, 1);
        grid.add(addButton, 0, 2);
        grid.add(cancelButton, 1, 2);

        Scene scene = new Scene(grid, 400, 200);
        addToCartStage.setScene(scene);
        addToCartStage.show();
    }
    private Scene createCartViewScene(Stage primaryStage, Cart cart) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        Label title = new Label("Your Cart");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        VBox cartItemsBox = new VBox(10);

        if (cart.isEmpty()) {
            Label emptyLabel = new Label("Your cart is empty.");
            cartItemsBox.getChildren().add(emptyLabel);
        } else {
            for (int i = 0; i < cart.getItems().size(); i++) {
                Product product = cart.getItems().get(i);
                int quantity = cart.getQuantities().get(i);

                HBox productBox = new HBox(10);
                productBox.setAlignment(Pos.CENTER_LEFT);

                Label productLabel = new Label(product.getName() + " x " + quantity +
                        " ($" + String.format("%.2f", product.getPrice()) + " each)");

                Button removeButton = new Button("Remove");
                removeButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
                removeButton.setOnAction(e -> {
                    cart.removeItem(product);
                    primaryStage.setScene(createCartViewScene(primaryStage, cart));
                });

                productBox.getChildren().addAll(productLabel, removeButton);
                cartItemsBox.getChildren().add(productBox);
            }

            Label totalLabel = new Label("Total: $" + String.format("%.2f", cart.calculateTotal()));
            totalLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
            cartItemsBox.getChildren().add(totalLabel);
        }

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> primaryStage.setScene(customerScene));

        layout.getChildren().addAll(title, cartItemsBox, backButton);
        return new Scene(layout, 500, 400);
    }
    private Scene createPlaceOrderScene(Stage primaryStage, Cart cart, Customer customer) {
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));

        Label title = new Label("Place Order");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label paymentLabel = new Label("Select Payment Method:");
        ComboBox<String> paymentComboBox = new ComboBox<>();
        for (PaymentMethod method : PaymentMethod.values()) {
            paymentComboBox.getItems().add(method.name());
        }
        paymentComboBox.setValue("CreditCard"); // Default value

        Label statusMessage = new Label();
        statusMessage.setStyle("-fx-font-size: 14px; -fx-text-fill: red;");

        Button placeOrderButton = new Button("Place Order");
        Button backButton = new Button("Back");

        // Button Actions
        placeOrderButton.setOnAction(e -> {
            if (cart.isEmpty()) {
                statusMessage.setText("Your cart is empty. Please add items before placing an order.");
                return;
            }

            double totalCost = cart.calculateTotal();
            if (customer.getBalance() < totalCost) {
                statusMessage.setText("Insufficient funds! You need $" +
                        String.format("%.2f", (totalCost - customer.getBalance())) + " more.");
            } else {
                // Deduct balance
                customer.setBalance(customer.getBalance() - totalCost);

                // Retrieve payment method
                PaymentMethod selectedMethod = PaymentMethod.valueOf(paymentComboBox.getValue());

                // Create order and store it in database
                Order newOrder = new Order(customer, selectedMethod, cart);
                Database.addOrder(newOrder);

                // Display the order receipt
                VBox receiptLayout = new VBox(10);
                receiptLayout.setPadding(new Insets(20));
                Label receiptTitle = new Label("Order Receipt");
                receiptTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

                Label receiptContent = new Label(newOrder.getReceipt());
                receiptContent.setStyle("-fx-font-family: monospace;");

                Button backToCustomerButton = new Button("Back");
                backToCustomerButton.setOnAction(ev -> primaryStage.setScene(customerScene));

                receiptLayout.getChildren().addAll(receiptTitle, receiptContent, backToCustomerButton);
                primaryStage.setScene(new Scene(receiptLayout, 500, 400));

                // Clear cart after placing the order
                cart.clearCart();
            }
        });

        backButton.setOnAction(e -> primaryStage.setScene(mainScene));

        // Add components to the layout
        layout.getChildren().addAll(title, paymentLabel, paymentComboBox, placeOrderButton, statusMessage, backButton);

        return new Scene(layout,500,400);
    }
    private Scene createProductListSceneCustomer(Stage primaryStage) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        Label title = new Label("Product List");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        ListView<String> productListView = new ListView<>();
        for (Product product : Database.getProducts()) {
            productListView.getItems().add("Product: " + product.getName() +
                    " | Price: $" + String.format("%.2f", product.getPrice()) +
                    " | Stock: " + product.getStockQuantity());
        }

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> primaryStage.setScene(customerScene));

        layout.getChildren().addAll(title, productListView, backButton);
        return new Scene(layout,500,400);
    }


    public static void main(String[] args) {
        launch();
    }
}
