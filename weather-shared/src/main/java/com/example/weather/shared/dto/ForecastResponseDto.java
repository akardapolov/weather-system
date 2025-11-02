package com.example.weather.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForecastResponseDto {
  private String stationId;
  private Instant generatedAt;
  private List<ForecastDto> forecasts;
}