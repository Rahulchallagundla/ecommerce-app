package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@SpringBootApplication
@RestController
@RequestMapping("/api")
public class EcommerceApplication {

    private Map<Integer, Product> products = new HashMap<>();

    public EcommerceApplication() {
        products.put(1, new Product(1, "Laptop", 55000));
        products.put(2, new Product(2, "Mobile", 20000));
        products.put(3, new Product(3, "Headphones", 2000));
    }

    // Health check
    @GetMapping("/health")
    public String health() {
        return "E-Commerce Service is Running 🚀";
    }

    // Get all products
    @GetMapping("/products")
    public Collection<Product> getProducts() {
        return products.values();
    }

    // Get product by ID
    @GetMapping("/products/{id}")
    public Product getProduct(@PathVariable int id) {
        return products.get(id);
    }

    // Add product (simulate POST)
    @PostMapping("/products")
    public String addProduct(@RequestBody Product product) {
        products.put(product.getId(), product);
        return "Product added successfully";
    }

    public static void main(String[] args) {
        SpringApplication.run(EcommerceApplication.class, args);
    }
}

// Simple Product Model
class Product {
    private int id;
    private String name;
    private double price;

    public Product() {}

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
