package ru.mina.test.kafka.mapper.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mina.test.kafka.dto.KafkaMessage;
import ru.mina.test.kafka.mapper.AbstractTest;
import ru.mina.test.kafka.annotation.ModelTest;
import ru.mina.test.kafka.model.Message;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * User: @AleksandrMIM
 * Date: 26.07.2020
 * Time: 18:37
 */
@ModelTest
class MessageDtoMapperTest extends AbstractTest {

  @Autowired
  private MessageDtoMapper messageDtoMapper;

  @ParameterizedTest
  @MethodSource("testData")
  void map_enterValue_successMapping(KafkaMessage kafkaMessage) {
    Message message = messageDtoMapper.map(kafkaMessage);
    assertEquals(message.getId(), kafkaMessage.getMessageId());
    assertEquals(message.getPayload(), kafkaMessage.getPayload());
  }

  @Test
  void map_nullValue_nullMapping() {
    Message message = messageDtoMapper.map(null);
    assertNull(message);
  }

  static Stream<KafkaMessage> testData() {
    return Stream.of(
        createDto(42, "49812"),
        createDto(-42, "498124444"),
        createDto(0, "498123213"),
        createDto(42, null),
        createDto(42, "")
    );
  }

  static KafkaMessage createDto(int id, String payload) {
    KafkaMessage kafkaMessage = new KafkaMessage();
    kafkaMessage.setMessageId(id);
    kafkaMessage.setPayload(payload);
    return kafkaMessage;
  }
}
