package ru.mina.test.kafka.dto;

import org.junit.jupiter.api.Test;
import ru.mina.test.kafka.annotation.ModelTest;
import ru.mina.test.kafka.mapper.AbstractTest;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * User: @AleksandrMIM
 * Date: 26.07.2020
 * Time: 19:48
 */
@ModelTest
class KafkaGroupMessageTest extends AbstractTest {

  @Test
  void validate_withoutMessages_false() {
    KafkaGroupMessage kafkaGroupMessage = new KafkaGroupMessage();
    assertFalse(kafkaGroupMessage.validate());
  }

  @Test
  void validate_withEmptyMessages_false() {
    KafkaGroupMessage kafkaGroupMessage = new KafkaGroupMessage();
    kafkaGroupMessage.setMessages(Collections.emptyList());
    assertFalse(kafkaGroupMessage.validate());
  }

  @Test
  void validate_withEmptyMessages_true() {
    KafkaGroupMessage kafkaGroupMessage = new KafkaGroupMessage();
    kafkaGroupMessage.setMessages(List.of(new KafkaMessage()));
    assertTrue(kafkaGroupMessage.validate());
  }
}