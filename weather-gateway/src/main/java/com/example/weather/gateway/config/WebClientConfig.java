package com.example.weather.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

  @Value("${weather.processing.service.url}")
  private String processingServiceUrl;

  @Bean
  public WebClient webClient() {
    return WebClient.builder()
        .baseUrl(processingServiceUrl)
        .build();
  }
}