package com.example.weather.processing.controller;

import com.example.weather.processing.service.ForecastService;
import com.example.weather.shared.dto.ForecastResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ForecastController {

  private final ForecastService forecastService;

  @GetMapping("/forecast")
  public ResponseEntity<ForecastResponseDto> getForecast(
      @RequestParam String stationId,
      @RequestParam(defaultValue = "12") int hours) {
    try {
      ForecastResponseDto forecast = forecastService.generateForecast(stationId, hours);
      return ResponseEntity.ok(forecast);
    } catch (IllegalStateException e) {
      // Если данных для прогноза недостаточно
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }
}