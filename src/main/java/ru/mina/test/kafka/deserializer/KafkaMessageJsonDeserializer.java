package ru.mina.test.kafka.deserializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import ru.mina.test.kafka.dto.KafkaGroupMessage;

/**
 * Deserialize data from kafka
 * User: @AleksandrMIM
 * Date: 23.07.2020
 * Time: 20:39
 */
@RequiredArgsConstructor
@Component
public class KafkaMessageJsonDeserializer implements KafkaMessageDeserializer {

  private final ObjectMapper objectMapper;

  @Override
  public @NotNull KafkaGroupMessage deserialize(String value) {
    try {
      return objectMapper.readValue(value, KafkaGroupMessage.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException();
    }
  }
}
