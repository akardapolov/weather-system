package com.example.weather.processing.repository;

import com.example.weather.processing.entity.WeatherData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {
  List<WeatherData> findByStationIdOrderByTimestampDesc(String stationId, Pageable pageable);
}