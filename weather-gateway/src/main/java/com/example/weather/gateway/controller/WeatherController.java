package com.example.weather.gateway.controller;

import com.example.weather.gateway.service.WeatherGatewayService;
import com.example.weather.shared.dto.ForecastResponseDto;
import com.example.weather.shared.dto.WeatherDataDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/weather")
@RequiredArgsConstructor
public class WeatherController {

  private final WeatherGatewayService weatherGatewayService;

  @PostMapping("/data")
  public ResponseEntity<Void> receiveWeatherData(@Valid @RequestBody WeatherDataDto data) {
    weatherGatewayService.sendWeatherData(data);
    return ResponseEntity.accepted().build();
  }

  @GetMapping("/forecast")
  public Mono<ForecastResponseDto> getForecast(
      @RequestParam String stationId,
      @RequestParam(defaultValue = "12") int hours) {
    return weatherGatewayService.getForecast(stationId, hours);
  }
}