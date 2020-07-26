package ru.mina.test.kafka.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Сообщение полученное из Kafka
 * User: @AleksandrMIM
 * Date: 21.07.2020
 * Time: 23:43
 */
@Data
@Slf4j
public class KafkaGroupMessage implements Validatable {
  /**
   * Список сообщений
   */
  private List<KafkaMessage> messages;

  @Override
  public boolean validate() {
    if (messages == null || messages.isEmpty()) {
      logger.info("Пустое тело сообщения");
      return false;
    } else {
      return true;
    }
  }
}
