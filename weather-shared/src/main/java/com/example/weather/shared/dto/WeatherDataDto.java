package com.example.weather.shared.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherDataDto implements Serializable {
  @NotBlank(message = "stationId is mandatory")
  private String stationId;

  @NotNull(message = "timestamp is mandatory")
  private Instant timestamp;

  @NotNull(message = "temperature is mandatory")
  private Double temperature;

  @NotNull(message = "humidity is mandatory")
  private Double humidity;

  @NotNull(message = "pressure is mandatory")
  private Double pressure;

  @NotNull(message = "precipitation is mandatory")
  private Double precipitation;
}