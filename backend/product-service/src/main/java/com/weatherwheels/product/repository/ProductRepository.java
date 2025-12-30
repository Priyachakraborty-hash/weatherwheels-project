package com.weatherwheels.product.repository;

import com.weatherwheels.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(String category);
    
    List<Product> findByType(String type);
    
    List<Product> findByCategoryAndType(String category, String type);
    
    @Query("SELECT p FROM Product p JOIN p.recommendedSeasons s WHERE s = ?1")
    List<Product> findByRecommendedSeason(String season);
    
    @Query("SELECT p FROM Product p JOIN p.suitableWeatherConditions w WHERE w = ?1")
    List<Product> findBySuitableWeatherCondition(String weatherCondition);
    
    List<Product> findByIsAvailableTrue();
    
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);
}
