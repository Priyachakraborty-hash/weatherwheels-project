package com.weatherwheels.weather.repository;

import com.weatherwheels.weather.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {
    List<Weather> findByLocation(String location);
    
    Optional<Weather> findTopByLocationOrderByRecordedAtDesc(String location);
    
    @Query("SELECT w FROM Weather w WHERE w.location = ?1 AND w.recordedAt >= ?2")
    List<Weather> findRecentWeatherByLocation(String location, LocalDateTime since);
    
    List<Weather> findByLocationAndSeason(String location, String season);
}
