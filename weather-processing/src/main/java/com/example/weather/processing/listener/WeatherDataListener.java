package com.example.weather.processing.listener;

import com.example.weather.processing.service.WeatherProcessingService;
import com.example.weather.shared.dto.WeatherDataDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class WeatherDataListener {

  private final WeatherProcessingService processingService;

  @RabbitListener(queues = "weather.data.queue")
  public void receiveMessage(WeatherDataDto data) {
    try {
      log.info("Received weather data for station {}: {}", data.getStationId(), data);
      processingService.processAndSave(data);
    } catch (Exception e) {
      log.error("Error processing message for station {}: {}", data.getStationId(), e.getMessage());
      // Здесь можно реализовать логику отправки в Dead Letter Queue, если она настроена
    }
  }
}