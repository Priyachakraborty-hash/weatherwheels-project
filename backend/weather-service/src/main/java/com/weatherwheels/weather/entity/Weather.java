package com.weatherwheels.weather.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "weather_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String location;
    
    @Column(nullable = false)
    private Double temperature;
    
    @Column(nullable = false)
    private Double humidity;
    
    @Column(nullable = false)
    private Double precipitation;
    
    @Column(nullable = false)
    private String condition;
    
    @Column(nullable = false)
    private String season;
    
    @Column(nullable = false)
    private Double windSpeed;
    
    private String forecast;
    
    @Column(columnDefinition = "TEXT")
    private String agricultureAdvice;
    
    @Column(nullable = false)
    private LocalDateTime recordedAt;
    
    private LocalDateTime forecastDate;
}
