package ru.mina.test.kafka.deserializer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mina.test.kafka.annotation.ModelTest;
import ru.mina.test.kafka.dto.KafkaGroupMessage;
import ru.mina.test.kafka.dto.KafkaMessage;
import ru.mina.test.kafka.mapper.AbstractTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * User: @AleksandrMIM
 * Date: 26.07.2020
 * Time: 19:22
 */
@ModelTest
class KafkaMessageDeserializerTest extends AbstractTest {

  @Autowired
  KafkaMessageDeserializer kafkaMessageDeserializer;

  @Test
  void deserialize_nullValue_emptyResult() {
    assertTrue(kafkaMessageDeserializer.deserialize(null).isEmpty());
  }

  @Test
  void deserialize_blankValue_emptyResult() {
    assertTrue(kafkaMessageDeserializer.deserialize("       ").isEmpty());
  }

  @Test
  void deserialize_incorrectValue_emptyResult() {
    assertTrue(kafkaMessageDeserializer.deserialize("{\"messages\": [{\"messageId\": integer, \"payload\":\"string\"}]}").isEmpty());
  }

  @Test
  void deserialize_correctValue_successDeserializeClass() {
    Optional<KafkaGroupMessage> optionalKafkaGroupMessage = kafkaMessageDeserializer.deserialize("{}");
    assertTrue(optionalKafkaGroupMessage.isPresent());
    assertTrue(optionalKafkaGroupMessage.map(KafkaGroupMessage::getMessages).isEmpty());
  }

  @Test
  void deserialize_correctValueWithoutMessages_successDeserializeClassWithNullMessages() {
    Optional<List<KafkaMessage>> optionalKafkaGroupMessage = kafkaMessageDeserializer.deserialize("{\"messages\": [{\"messageId\": 30, \"payload\":\"string\"}]}")
        .map(KafkaGroupMessage::getMessages);
    assertTrue(optionalKafkaGroupMessage.isPresent());

    KafkaMessage kafkaMessage = optionalKafkaGroupMessage.get().get(0);
    assertEquals(kafkaMessage.getMessageId(), 30);
    assertEquals(kafkaMessage.getPayload(), "string");
  }
}