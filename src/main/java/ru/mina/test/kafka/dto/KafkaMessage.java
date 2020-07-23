package ru.mina.test.kafka.dto;

import lombok.Data;

/**
 * Сообщение
 * User: @AleksandrMIM
 * Date: 21.07.2020
 * Time: 23:41
 */
@Data
public class KafkaMessage {
  /**
   * Идентификатор сообщения
   */
  private int messageId;
  /**
   * Содержимое сообщения
   */
  private String payload;
}
