package com.weatherwheels.product.controller;

import com.weatherwheels.product.entity.Product;
import com.weatherwheels.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {
    private final ProductService productService;
    
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }
    
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(productService.getProductsByCategory(category));
    }
    
    @GetMapping("/type/{type}")
    public ResponseEntity<List<Product>> getProductsByType(@PathVariable String type) {
        return ResponseEntity.ok(productService.getProductsByType(type));
    }
    
    @GetMapping("/season/{season}")
    public ResponseEntity<List<Product>> getProductsBySeason(@PathVariable String season) {
        return ResponseEntity.ok(productService.getProductsBySeason(season));
    }
    
    @GetMapping("/weather/{condition}")
    public ResponseEntity<List<Product>> getProductsByWeatherCondition(@PathVariable String condition) {
        return ResponseEntity.ok(productService.getProductsByWeatherCondition(condition));
    }
    
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.saveProduct(product));
    }
}
