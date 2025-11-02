package com.example.weather.gateway.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

  public static final String QUEUE_NAME = "weather.data.queue";

  @Bean
  public Queue weatherDataQueue() {
    // durable: true - очередь переживет перезапуск брокера
    return new Queue(QUEUE_NAME, true);
  }

  @Bean
  public MessageConverter jsonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }
}