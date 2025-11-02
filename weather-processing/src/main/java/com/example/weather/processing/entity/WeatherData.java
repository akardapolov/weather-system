package com.example.weather.processing.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;

@Entity
@Table(name = "weather_data")
@Data
public class WeatherData {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String stationId;
  private Instant timestamp;
  private Double temperature;
  private Double humidity;
  private Double pressure;
  private Double precipitation;
}