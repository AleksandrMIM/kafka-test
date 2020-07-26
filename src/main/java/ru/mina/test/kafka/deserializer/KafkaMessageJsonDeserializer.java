package ru.mina.test.kafka.deserializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import ru.mina.test.kafka.dto.KafkaGroupMessage;

import java.util.Optional;

/**
 * Deserialize data from kafka
 * User: @AleksandrMIM
 * Date: 23.07.2020
 * Time: 20:39
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class KafkaMessageJsonDeserializer implements KafkaMessageDeserializer {

  private final ObjectMapper objectMapper;

  @Override
  public @NotNull Optional<KafkaGroupMessage> deserialize(String value) {
    if (value == null || value.isBlank()) {
      return Optional.empty();
    }

    try {
      return Optional.ofNullable(objectMapper.readValue(value, KafkaGroupMessage.class));
    } catch (JsonProcessingException e) {
      logger.warn("Неверный формат входящего сообщения", e);

      return Optional.empty();
    }
  }
}
