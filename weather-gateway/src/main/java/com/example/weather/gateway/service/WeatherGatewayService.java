package com.example.weather.gateway.service;

import com.example.weather.gateway.config.RabbitMqConfig;
import com.example.weather.shared.dto.ForecastResponseDto;
import com.example.weather.shared.dto.WeatherDataDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherGatewayService {

  private final RabbitTemplate rabbitTemplate;
  private final WebClient webClient;

  public void sendWeatherData(WeatherDataDto data) {
    log.info("Sending weather data for station {}: {}", data.getStationId(), data);
    rabbitTemplate.convertAndSend(RabbitMqConfig.QUEUE_NAME, data);
  }

  public Mono<ForecastResponseDto> getForecast(String stationId, int hours) {
    log.info("Requesting forecast for station {} for {} hours", stationId, hours);
    return webClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/api/v1/forecast")
            .queryParam("stationId", stationId)
            .queryParam("hours", hours)
            .build())
        .retrieve()
        .bodyToMono(ForecastResponseDto.class)
        .doOnError(error -> log.error("Error fetching forecast for station {}: {}", stationId, error.getMessage()));
  }
}