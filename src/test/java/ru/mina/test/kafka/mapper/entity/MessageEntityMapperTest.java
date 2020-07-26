package ru.mina.test.kafka.mapper.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mina.test.kafka.entity.MessageEntity;
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
class MessageEntityMapperTest extends AbstractTest {

  @Autowired
  private MessageEntityMapper messageEntityMapper;

  @ParameterizedTest
  @MethodSource("testData")
  void map_enterValue_successMapping(Message message) {
    MessageEntity messageEntity = messageEntityMapper.map(message);
    assertEquals(messageEntity.getId(), message.getId());
    assertEquals(messageEntity.getPayload(), message.getPayload());
  }

  @Test
  void map_nullValue_nullMapping() {
    MessageEntity messageEntity = messageEntityMapper.map(null);
    assertNull(messageEntity);
  }

  static Stream<Message> testData() {
    return Stream.of(
        createModel(42, "49812"),
        createModel(-42, "498124444"),
        createModel(0, "498123213"),
        createModel(42, null),
        createModel(42, "")
    );
  }

  static Message createModel(int id, String payload) {
    Message message = new Message();
    message.setId(id);
    message.setPayload(payload);
    return message;
  }
}
