package ru.mina.test.kafka.deserializer;

import org.jetbrains.annotations.NotNull;
import ru.mina.test.kafka.dto.KafkaGroupMessage;

/**
 * Deserialize data from kafka
 * User: @AleksandrMIM
 * Date: 23.07.2020
 * Time: 20:25
 */
public interface KafkaMessageDeserializer {

  /**
   * Десериализация данных из kafka
   *
   * @param value значение
   * @return сообщение
   */
  @NotNull
  KafkaGroupMessage deserialize(String value);
}
