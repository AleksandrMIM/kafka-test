package ru.mina.test.kafka.converter;

import org.jetbrains.annotations.NotNull;
import ru.mina.test.kafka.model.Message;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * Конвертер сообщения kafka
 * User: @AleksandrMIM
 * Date: 26.07.2020
 * Time: 16:57
 */
public interface KafkaMessageConverter {

  /**
   * Преобразование листа строк к доменным сущностям
   *
   * @param values строки
   * @return стрим
   */
  @NotNull
  Stream<Message> convert(Collection<String> values);
}
