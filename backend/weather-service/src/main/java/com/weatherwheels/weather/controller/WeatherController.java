package com.weatherwheels.weather.controller;

import com.weatherwheels.weather.entity.Weather;
import com.weatherwheels.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class WeatherController {
    private final WeatherService weatherService;
    
    @GetMapping("/current/{location}")
    public ResponseEntity<Weather> getCurrentWeather(@PathVariable String location) {
        return ResponseEntity.ok(weatherService.getCurrentWeather(location));
    }
    
    @GetMapping("/forecast/{location}")
    public ResponseEntity<List<Weather>> getWeatherForecast(
            @PathVariable String location,
            @RequestParam(defaultValue = "7") int days) {
        return ResponseEntity.ok(weatherService.getWeatherForecast(location, days));
    }
    
    @GetMapping("/seasonal/{location}/{season}")
    public ResponseEntity<Weather> getSeasonalWeather(
            @PathVariable String location,
            @PathVariable String season) {
        return ResponseEntity.ok(weatherService.getWeatherBySeason(location, season));
    }
}
