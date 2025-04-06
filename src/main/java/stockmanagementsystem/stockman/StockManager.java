package stockmanagementsystem.stockman;

import java.util.ArrayList;
import java.util.List;

public class StockManager {
    private final List<Product> productList = new ArrayList<>();

    public void addProduct(Product product) {
        productList.add(product);
    }

    public void removeProduct(String productId) {
        productList.removeIf(product -> product.getProductId().equals(productId));
    }

    public List<Product> getAllProducts() {
        return productList;
    }

    public double calculateTotalPrice() {
        return productList.stream().mapToDouble(Product::getTotal).sum();
    }
}