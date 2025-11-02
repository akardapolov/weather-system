// filename: ForecastService.java
package com.example.weather.processing.service;

import com.example.weather.processing.entity.WeatherData;
import com.example.weather.processing.repository.WeatherDataRepository;
import com.example.weather.shared.dto.ForecastDto;
import com.example.weather.shared.dto.ForecastResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ForecastService {

  private final WeatherDataRepository repository;

  @Value("${forecast.history.size:20}")
  private int historySize;

  public ForecastResponseDto generateForecast(String stationId, int hours) {
    List<WeatherData> recentData = repository.findByStationIdOrderByTimestampDesc(
        stationId, PageRequest.of(0, historySize));

    if (recentData.size() < 2) {
      throw new IllegalStateException("Not enough data to generate forecast for station: " + stationId);
    }

    // Данные отсортированы по убыванию времени, берем самый новый и самый старый
    WeatherData newest = recentData.get(0);
    WeatherData oldest = recentData.get(recentData.size() - 1);

    List<ForecastDto> forecasts = new ArrayList<>();
    Instant lastKnownTime = newest.getTimestamp();

    for (int i = 1; i <= hours; i++) {
      Instant forecastTime = lastKnownTime.plus(i, ChronoUnit.HOURS);
      forecasts.add(createForecastForTime(forecastTime, oldest, newest));
    }

    return new ForecastResponseDto(stationId, Instant.now(), forecasts);
  }

  private ForecastDto createForecastForTime(Instant forecastTime, WeatherData oldest, WeatherData newest) {
    ForecastDto forecast = new ForecastDto();
    forecast.setTimestamp(forecastTime);
    forecast.setTemperature(extrapolate(forecastTime, oldest, newest, WeatherData::getTemperature, "temperature"));
    forecast.setHumidity(extrapolate(forecastTime, oldest, newest, WeatherData::getHumidity, "humidity"));
    forecast.setPressure(extrapolate(forecastTime, oldest, newest, WeatherData::getPressure, "pressure"));
    forecast.setPrecipitation(extrapolate(forecastTime, oldest, newest, WeatherData::getPrecipitation, "precipitation"));
    return forecast;
  }

  private Double extrapolate(Instant forecastTime, WeatherData oldest, WeatherData newest,
                             Function<WeatherData, Double> valueExtractor, String fieldType) {
    long x1 = oldest.getTimestamp().getEpochSecond();
    double y1 = valueExtractor.apply(oldest);
    long x2 = newest.getTimestamp().getEpochSecond();
    double y2 = valueExtractor.apply(newest);
    long x_forecast = forecastTime.getEpochSecond();

    // Простая линейная экстраполяция: y = y1 + (x - x1) * (y2 - y1) / (x2 - x1)
    double slope = (y2 - y1) / (x2 - x1);
    double extrapolatedValue = y1 + (x_forecast - x1) * slope;

    // Ограничиваем значения разумными рамками в зависимости от типа поля
    if ("humidity".equals(fieldType)) {
      return Math.max(0, Math.min(100, extrapolatedValue));
    }
    if ("precipitation".equals(fieldType)) {
      return Math.max(0, extrapolatedValue);
    }
    return extrapolatedValue;
  }
}