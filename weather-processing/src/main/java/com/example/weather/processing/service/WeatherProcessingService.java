package com.example.weather.processing.service;

import com.example.weather.processing.entity.WeatherData;
import com.example.weather.processing.repository.WeatherDataRepository;
import com.example.weather.shared.dto.WeatherDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherProcessingService {

  private final WeatherDataRepository repository;

  public void processAndSave(WeatherDataDto dto) {
    WeatherData entity = new WeatherData();
    entity.setStationId(dto.getStationId());
    entity.setTimestamp(dto.getTimestamp());
    entity.setTemperature(dto.getTemperature());
    entity.setHumidity(dto.getHumidity());
    entity.setPressure(dto.getPressure());
    entity.setPrecipitation(dto.getPrecipitation());
    repository.save(entity);
  }
}