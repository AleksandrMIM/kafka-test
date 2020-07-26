package ru.mina.test.kafka.dto;

import org.junit.jupiter.api.Test;
import ru.mina.test.kafka.annotation.ModelTest;
import ru.mina.test.kafka.mapper.AbstractTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * User: @AleksandrMIM
 * Date: 26.07.2020
 * Time: 19:49
 */
@ModelTest
class KafkaMessageTest extends AbstractTest {

  @Test
  void validate_withoutInit_true() {
    KafkaMessage kafkaMessage = new KafkaMessage();
    assertTrue(kafkaMessage.validate());
  }

  @Test
  void validate_withNegativeId_false() {
    KafkaMessage kafkaMessage = new KafkaMessage();
    kafkaMessage.setMessageId(-23);
    assertFalse(kafkaMessage.validate());
  }

  @Test
  void validate_withPositiveId_false() {
    KafkaMessage kafkaMessage = new KafkaMessage();
    kafkaMessage.setMessageId(23);
    assertTrue(kafkaMessage.validate());
  }

  @Test
  void validate_withPositiveIdAndNotNullPayload_false() {
    KafkaMessage kafkaMessage = new KafkaMessage();
    kafkaMessage.setMessageId(23);
    kafkaMessage.setPayload("3912941");
    assertTrue(kafkaMessage.validate());
  }
}