package ru.mina.test.kafka.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Сообщение
 * User: @AleksandrMIM
 * Date: 21.07.2020
 * Time: 23:41
 */
@Data
@Slf4j
public class KafkaMessage implements Validatable {
  /**
   * Идентификатор сообщения
   */
  private int messageId;
  /**
   * Содержимое сообщения
   */
  private String payload;

  @Override
  public boolean validate() {
    if (messageId < 0) {
      logger.warn("Некоректный идентификатор {} сообщения {}", messageId, payload);
      return false;
    } else {
      return true;
    }
  }
}
