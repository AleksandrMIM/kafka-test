package ru.mina.test.kafka.dto;

import lombok.Data;

import java.util.List;

/**
 * Сообщение полученное из Kafka
 * User: @AleksandrMIM
 * Date: 21.07.2020
 * Time: 23:43
 */
@Data
public class KafkaGroupMessage {
  /**
   * Список сообщений
   */
  private List<KafkaMessage> messages;
}
