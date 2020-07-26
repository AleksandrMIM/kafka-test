package ru.mina.test.kafka.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * User: @AleksandrMIM
 * Date: 26.07.2020
 * Time: 14:33
 */
@Configuration
public class KafkaTopicConfiguration {

  @Value("${spring.kafka.partitions}")
  private int partitions;
  @Value("${spring.kafka.topic}")
  private String topic;

  @Bean
  public NewTopic topicExample() {
    return TopicBuilder.name(topic)
        .partitions(partitions)
        .build();
  }
}
