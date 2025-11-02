package com.example.weather.processing.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

  public static final String QUEUE_NAME = "weather.data.queue";

  /**
   * Declares the queue. If the queue doesn't exist, it will be created.
   * This is an idempotent operation.
   */
  @Bean
  public Queue weatherDataQueue() {
    return new Queue(QUEUE_NAME, true);
  }

  /**
   * Provides a message converter to automatically deserialize the JSON payload
   * of the message back into a WeatherDataDto object for the listener.
   */
  @Bean
  public MessageConverter jsonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }
}