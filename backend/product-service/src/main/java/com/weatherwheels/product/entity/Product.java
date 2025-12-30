package com.weatherwheels.product.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String n;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(nullable = false)
    private String category;
    
    @Column(nullable = false)
    private String type;
    
    @Column(nullable = false)
    private BigDecimal price;
    
    @Column(nullable = false)
    private Integer stockQuantity;
    
    private String brand;
    
    private String model;
    
    @ElementCollection
    @CollectionTable(name = "product_features", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "feature")
    private List<String> features;
    
    @ElementCollection
    @CollectionTable(name = "product_weather_conditions", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "weather_condition")
    private List<String> suitableWeatherConditions;
    
    @ElementCollection
    @CollectionTable(name = "product_seasons", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "season")
    private List<String> recommendedSeasons;
    
    private Double powerRating;
    
    private String fuelType;
    
    private Double efficiency;
    
    private String imageUrl;
    
    @Column(nullable = false)
    private Boolean isAvailable;
    
    private Double rating;
    
    private Integer reviewCount;
}
