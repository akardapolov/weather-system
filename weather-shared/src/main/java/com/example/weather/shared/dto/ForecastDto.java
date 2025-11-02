package com.example.weather.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForecastDto {
  private Instant timestamp;
  private Double temperature;
  private Double humidity;
  private Double pressure;
  private Double precipitation;
}