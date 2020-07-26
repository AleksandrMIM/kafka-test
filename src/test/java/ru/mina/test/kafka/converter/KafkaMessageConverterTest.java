package ru.mina.test.kafka.converter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mina.test.kafka.annotation.ModelTest;
import ru.mina.test.kafka.mapper.AbstractTest;
import ru.mina.test.kafka.model.Message;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * User: @AleksandrMIM
 * Date: 26.07.2020
 * Time: 19:31
 */
@ModelTest
class KafkaMessageConverterTest extends AbstractTest {

  @Autowired
  private KafkaMessageConverter kafkaMessageConverter;

  @Test
  void convert_nullList_emptyStream() {
    assertEquals(kafkaMessageConverter.convert(null).count(), 0);
  }

  @Test
  void convert_emptyList_emptyStream() {
    assertEquals(kafkaMessageConverter.convert(Collections.emptyList()).count(), 0);
  }

  @Test
  void convert_correctValue_successConvert() {
    List<String> values = List.of(
        "{\"messages\": [{\"messageId\": 0, \"payload\":\"string1\"}]}",
        "{\"messages\": [{\"messageId\": 42, \"payload\":\"string2\"}]}",
        "{}",
        "{\"messages\": []}",
        "{\"messages\": [{\"messageId\": -20, \"payload\":\"string\"}]}"
    );
    List<Message> messageStream = kafkaMessageConverter.convert(values)
        .collect(Collectors.toList());

    assertEquals(messageStream.size(), 2);
    checkMessage(messageStream, 0, 0, "string1");
    checkMessage(messageStream, 1, 42, "string2");
  }

  private void checkMessage(List<Message> messageStream, int number, int id, String payload) {
    Message message = messageStream.get(number);
    assertEquals(message.getId(), id);
    assertEquals(message.getPayload(), payload);
  }
}