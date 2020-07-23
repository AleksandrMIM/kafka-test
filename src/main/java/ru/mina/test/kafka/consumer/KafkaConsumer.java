package ru.mina.test.kafka.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import ru.mina.test.kafka.deserializer.KafkaMessageDeserializer;
import ru.mina.test.kafka.dto.KafkaGroupMessage;
import ru.mina.test.kafka.interactor.MessageInteractor;
import ru.mina.test.kafka.mapper.dto.MessageDtoMapper;
import ru.mina.test.kafka.model.Message;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * User: @AleksandrMIM
 * Date: 27.06.2020
 * Time: 12:23
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

  private final KafkaMessageDeserializer deserializer;
  private final MessageDtoMapper dtoMapper;
  private final MessageInteractor messageInteractor;

  @KafkaListener(id = "batch-listener", topics = "testTopic", containerFactory = "kafkaListenerContainerFactory")
  public void consume(@Payload List<String> values) {
    logger.info(String.format("#### -> Consumed message -> %s", String.join("\n", values)));

    List<Message> messageList = values.stream()
        .map(deserializer::deserialize)
        .map(KafkaGroupMessage::getMessages)
        .filter(Objects::nonNull)
        .flatMap(Collection::stream)
        .map(dtoMapper::map)
        .collect(Collectors.toList());
    messageInteractor.saveMessage(messageList);
  }
}
