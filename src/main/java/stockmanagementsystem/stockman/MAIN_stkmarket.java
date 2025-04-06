package stockmanagementsystem.stockman;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MAIN_stkmarket extends Application {
    private final StockManager stockManager = new StockManager();
    private TableView<Product> tableView;
    private TextField idField, nameField, quantityField, priceField;
    private Label totalPriceLabel;

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Stock Management System");

        Label titleLabel = new Label("WELCOME TO FINANCIAL PULSE");
        titleLabel.setPadding(new Insets(10));
        titleLabel.setTextFill(Color.BURLYWOOD);
        titleLabel.setFont(Font.font("Times New Roman", 24));
        titleLabel.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(25), Insets.EMPTY)));

        // TableView for displaying products
        tableView = new TableView<>();
        tableView.setItems(getProductList());
        tableView.getColumns().addAll(
                createColumn("ID", "productId"),
                createColumn("Name", "productName"),
                createColumn("Quantity", "quantity"),
                createColumn("Price", "price"),
                createColumn("Total", "total")  // Add Total column
        );

        // Input fields and buttons
        idField = new TextField();
        idField.setPromptText("Product ID");

        nameField = new TextField();
        nameField.setPromptText("Product Name");

        quantityField = new TextField();
        quantityField.setPromptText("Quantity");

        priceField = new TextField();
        priceField.setPromptText("Price");

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> addProduct());

        Button removeButton = new Button("Remove");
        removeButton.setOnAction(e -> removeProduct());

        // Total price label
        totalPriceLabel = new Label("Total Price: Rs 0.0");
        updateTotalPrice(); // Initialize total price

        // Layout setup
        HBox inputFields = new HBox(10, idField, nameField, quantityField, priceField, addButton, removeButton);
        inputFields.setPadding(new Insets(10));

        VBox layout = new VBox(10, titleLabel, tableView, inputFields, totalPriceLabel);
        layout.setPadding(new Insets(10));

        Scene scene = new Scene(layout, 850, 500, Color.DARKBLUE);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private ObservableList<Product> getProductList() {
        return FXCollections.observableArrayList(stockManager.getAllProducts());
    }

    private TableColumn<Product, String> createColumn(String title, String property) {
        TableColumn<Product, String> column = new TableColumn<>(title);
        column.setCellValueFactory(new PropertyValueFactory<>(property));
        return column;
    }

    private void addProduct() {
        try {
            String id = idField.getText();
            String name = nameField.getText();
            int quantity = Integer.parseInt(quantityField.getText());
            double price = Double.parseDouble(priceField.getText());
            double total = price * quantity;

            Product product = new Product(id, name, quantity, price, total);
            stockManager.addProduct(product);
            tableView.getItems().add(product);

            clearFields();
            updateTotalPrice(); // Update total price after adding
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter valid numbers for Quantity and Price.");
            alert.showAndWait();
        }
    }

    private void removeProduct() {
        Product selectedProduct = tableView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            stockManager.removeProduct(selectedProduct.getProductId());
            tableView.getItems().remove(selectedProduct);
            updateTotalPrice(); // Update total price after removing
        }
    }

    private void updateTotalPrice() {
        double totalPrice = stockManager.calculateTotalPrice();
        totalPriceLabel.setText("Total Price: Rs " + totalPrice);
    }

    private void clearFields() {
        idField.clear();
        nameField.clear();
        quantityField.clear();
        priceField.clear();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
