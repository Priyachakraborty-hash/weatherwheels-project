package com.weatherwheels.product.service;

import com.weatherwheels.product.entity.Product;
import com.weatherwheels.product.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    
    @PostConstruct
    public void initProducts() {
        if (productRepository.count() == 0) {
            loadInitialProducts();
        }
    }
    
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }
    
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }
    
    public List<Product> getProductsByType(String type) {
        return productRepository.findByType(type);
    }
    
    public List<Product> getProductsBySeason(String season) {
        return productRepository.findByRecommendedSeason(season.toUpperCase());
    }
    
    public List<Product> getProductsByWeatherCondition(String condition) {
        return productRepository.findBySuitableWeatherCondition(condition);
    }
    
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
    
    private void loadInitialProducts() {
        List<Product> products = new ArrayList<>();
        
        // Tractors
        products.add(createProduct("John Deere 5075E Tractor", "Heavy-duty tractor for all seasons", 
                "TRACTORS", "UTILITY", new BigDecimal("45000"), 5,
                Arrays.asList("75HP Engine", "4WD", "Power Steering", "Hydraulic System"),
                Arrays.asList("All Weather"), Arrays.asList("SPRING", "SUMMER", "AUTUMN"),
                75.0, "Diesel", 4.5));
        
        products.add(createProduct("Massey Ferguson 4710", "Compact utility tractor", 
                "TRACTORS", "COMPACT", new BigDecimal("35000"), 8,
                Arrays.asList("100HP Engine", "Cabin with AC", "GPS Ready"),
                Arrays.asList("Sunny", "Partly Cloudy"), Arrays.asList("SPRING", "SUMMER"),
                100.0, "Diesel", 4.7));
        
        // Harvesters
        products.add(createProduct("Case IH Axial-Flow 250", "High-capacity combine harvester", 
                "HARVESTERS", "COMBINE", new BigDecimal("450000"), 2,
                Arrays.asList("GPS Guidance", "Yield Monitoring", "Auto-Steering"),
                Arrays.asList("Sunny", "Partly Cloudy"), Arrays.asList("SUMMER", "AUTUMN"),
                550.0, "Diesel", 4.8));
        
        // Seeders
        products.add(createProduct("Great Plains 3S-4000HD", "Heavy-duty drill seeder", 
                "SEEDERS", "DRILL", new BigDecimal("85000"), 4,
                Arrays.asList("40ft Width", "Precision Seeding", "Variable Rate"),
                Arrays.asList("Partly Cloudy", "Cloudy"), Arrays.asList("SPRING"),
                0.0, "Hydraulic", 4.6));
        
        // Irrigation
        products.add(createProduct("Valley 8000 Series Pivot", "Center pivot irrigation system", 
                "IRRIGATION", "PIVOT", new BigDecimal("150000"), 3,
                Arrays.asList("Remote Control", "Variable Rate Irrigation", "GPS Positioning"),
                Arrays.asList("Sunny", "Hot"), Arrays.asList("SUMMER"),
                0.0, "Electric", 4.9));
        
        products.add(createProduct("Rain Bird Drip System", "Efficient drip irrigation", 
                "IRRIGATION", "DRIP", new BigDecimal("15000"), 10,
                Arrays.asList("Water Efficient", "Automated Timer", "Pressure Compensating"),
                Arrays.asList("Sunny", "Partly Cloudy"), Arrays.asList("SPRING", "SUMMER"),
                0.0, "Electric", 4.4));
        
        // Tillers
        products.add(createProduct("Kuhn Krause 8005 Excelerator", "Vertical tillage tool", 
                "TILLERS", "VERTICAL", new BigDecimal("75000"), 6,
                Arrays.asList("30ft Width", "Hydraulic Down Pressure", "Star Wheel Treaders"),
                Arrays.asList("Cloudy", "Partly Cloudy"), Arrays.asList("SPRING", "AUTUMN"),
                0.0, "Hydraulic", 4.5));
        
        // Sprayers
        products.add(createProduct("Apache AS1250 Sprayer", "Self-propelled sprayer", 
                "SPRAYERS", "SELF_PROPELLED", new BigDecimal("280000"), 3,
                Arrays.asList("1200 Gallon Tank", "120ft Boom", "GPS Guidance"),
                Arrays.asList("Calm", "Partly Cloudy"), Arrays.asList("SPRING", "SUMMER"),
                260.0, "Diesel", 4.7));
        
        // Plows
        products.add(createProduct("Kverneland 150 B Plow", "Reversible plow", 
                "PLOWS", "REVERSIBLE", new BigDecimal("45000"), 7,
                Arrays.asList("5-Furrow", "Auto-Reset", "Vari-Width"),
                Arrays.asList("Cloudy", "After Rain"), Arrays.asList("SPRING", "AUTUMN"),
                0.0, "Hydraulic", 4.3));
        
        // Mowers
        products.add(createProduct("New Holland Discbine 313", "Disc mower conditioner", 
                "MOWERS", "DISC", new BigDecimal("42000"), 5,
                Arrays.asList("13ft Cutting Width", "Rubber Rollers", "Quick-Change Knives"),
                Arrays.asList("Sunny", "Partly Cloudy"), Arrays.asList("SUMMER"),
                0.0, "Hydraulic", 4.6));
        
        productRepository.saveAll(products);
    }
    
    private Product createProduct(String name, String description, String category, String type,
                                 BigDecimal price, Integer stock, List<String> features,
                                 List<String> weatherConditions, List<String> seasons,
                                 Double powerRating, String fuelType, Double rating) {
        Product product = new Product();
        product.setN(name);
        product.setDescription(description);
        product.setCategory(category);
        product.setType(type);
        product.setPrice(price);
        product.setStockQuantity(stock);
        product.setFeatures(features);
        product.setSuitableWeatherConditions(weatherConditions);
        product.setRecommendedSeasons(seasons);
        product.setPowerRating(powerRating);
        product.setFuelType(fuelType);
        product.setRating(rating);
        product.setReviewCount(new Random().nextInt(50) + 10);
        product.setIsAvailable(true);
        product.setEfficiency(3.5 + new Random().nextDouble() * 1.5);
        
        // Generate image URL based on category
        String imageBase = "https://images.unsplash.com/photo-";
        Map<String, String> categoryImages = Map.of(
            "TRACTORS", "1516084525086-dea1c3",
            "HARVESTERS", "1574943320303-ad0d8e",
            "SEEDERS", "1625758478341-c5b2b0",
            "IRRIGATION", "1563514227441-42ae0b",
            "TILLERS", "1564510227400-87ae0b"
        );
        product.setImageUrl(imageBase + categoryImages.getOrDefault(category, "1516084525086-dea1c3") + "?w=800");
        
        return product;
    }
}
