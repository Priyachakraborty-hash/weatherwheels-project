package com.weatherwheels.weather.service;

import com.weatherwheels.weather.entity.Weather;
import com.weatherwheels.weather.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private final WeatherRepository weatherRepository;
    private final Random random = new Random();
    
    public Weather getCurrentWeather(String location) {
        // Check if we have recent data
        Optional<Weather> recent = weatherRepository.findTopByLocationOrderByRecordedAtDesc(location);
        
        if (recent.isPresent() && 
            recent.get().getRecordedAt().isAfter(LocalDateTime.now().minusHours(1))) {
            return recent.get();
        }
        
        // Generate new weather data (simulated)
        Weather weather = generateWeatherData(location);
        return weatherRepository.save(weather);
    }
    
    public List<Weather> getWeatherForecast(String location, int days) {
        List<Weather> forecast = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        
        for (int i = 0; i < days; i++) {
            Weather weather = generateWeatherData(location);
            weather.setForecastDate(now.plusDays(i));
            weather.setForecast("Day " + (i + 1) + " forecast");
            forecast.add(weatherRepository.save(weather));
        }
        
        return forecast;
    }
    
    public Weather getWeatherBySeason(String location, String season) {
        List<Weather> seasonalWeather = weatherRepository.findByLocationAndSeason(location, season);
        
        if (seasonalWeather.isEmpty()) {
            Weather weather = generateSeasonalWeather(location, season);
            return weatherRepository.save(weather);
        }
        
        return seasonalWeather.get(0);
    }
    
    private Weather generateWeatherData(String location) {
        Weather weather = new Weather();
        weather.setLocation(location);
        weather.setTemperature(15 + random.nextDouble() * 20);
        weather.setHumidity(40 + random.nextDouble() * 40);
        weather.setPrecipitation(random.nextDouble() * 10);
        weather.setWindSpeed(5 + random.nextDouble() * 20);
        weather.setRecordedAt(LocalDateTime.now());
        
        // Determine condition
        String[] conditions = {"Sunny", "Partly Cloudy", "Cloudy", "Rainy", "Stormy"};
        weather.setCondition(conditions[random.nextInt(conditions.length)]);
        
        // Determine season based on month
        weather.setSeason(determineSeason());
        
        // Generate agriculture advice based on conditions
        weather.setAgricultureAdvice(generateAgricultureAdvice(weather));
        
        return weather;
    }
    
    private Weather generateSeasonalWeather(String location, String season) {
        Weather weather = new Weather();
        weather.setLocation(location);
        weather.setSeason(season);
        weather.setRecordedAt(LocalDateTime.now());
        
        switch(season.toUpperCase()) {
            case "SPRING":
                weather.setTemperature(15 + random.nextDouble() * 10);
                weather.setHumidity(50 + random.nextDouble() * 20);
                weather.setPrecipitation(2 + random.nextDouble() * 5);
                weather.setCondition("Mild");
                weather.setAgricultureAdvice("Perfect for planting! Consider seeders and tillers.");
                break;
            case "SUMMER":
                weather.setTemperature(25 + random.nextDouble() * 10);
                weather.setHumidity(30 + random.nextDouble() * 20);
                weather.setPrecipitation(random.nextDouble() * 3);
                weather.setCondition("Hot");
                weather.setAgricultureAdvice("Irrigation equipment essential. Consider harvesters.");
                break;
            case "AUTUMN":
                weather.setTemperature(10 + random.nextDouble() * 10);
                weather.setHumidity(60 + random.nextDouble() * 20);
                weather.setPrecipitation(3 + random.nextDouble() * 5);
                weather.setCondition("Cool");
                weather.setAgricultureAdvice("Harvest time! Combines and storage equipment needed.");
                break;
            case "WINTER":
                weather.setTemperature(-5 + random.nextDouble() * 10);
                weather.setHumidity(70 + random.nextDouble() * 20);
                weather.setPrecipitation(5 + random.nextDouble() * 5);
                weather.setCondition("Cold");
                weather.setAgricultureAdvice("Equipment maintenance season. Snow plows may be needed.");
                break;
        }
        
        weather.setWindSpeed(5 + random.nextDouble() * 15);
        return weather;
    }
    
    private String determineSeason() {
        int month = LocalDateTime.now().getMonthValue();
        if (month >= 3 && month <= 5) return "SPRING";
        if (month >= 6 && month <= 8) return "SUMMER";
        if (month >= 9 && month <= 11) return "AUTUMN";
        return "WINTER";
    }
    
    private String generateAgricultureAdvice(Weather weather) {
        StringBuilder advice = new StringBuilder();
        
        if (weather.getTemperature() > 25) {
            advice.append("High temperature detected. Irrigation systems recommended. ");
        }
        if (weather.getPrecipitation() > 5) {
            advice.append("Heavy rain expected. Drainage equipment may be needed. ");
        }
        if (weather.getHumidity() > 70) {
            advice.append("High humidity. Consider equipment with rust protection. ");
        }
        if (weather.getWindSpeed() > 20) {
            advice.append("Strong winds. Secure lightweight equipment. ");
        }
        
        if (advice.length() == 0) {
            advice.append("Good conditions for general farming activities.");
        }
        
        return advice.toString();
    }
}
