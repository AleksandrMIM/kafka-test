package ru.mina.test.kafka.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import ru.mina.test.kafka.converter.KafkaMessageConverter;
import ru.mina.test.kafka.interactor.MessageInteractor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * User: @AleksandrMIM
 * Date: 27.06.2020
 * Time: 12:23
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {

  private final KafkaMessageConverter kafkaMessageConverter;
  private final MessageInteractor messageInteractor;

  @KafkaListener(id = "batch-listener", topics = "${spring.kafka.topic}", containerFactory = "kafkaListenerContainerFactory")
  public void consume(@Payload List<String> values) {
    if (logger.isInfoEnabled()) {
      for (String value : values) {
        logger.info("Получено сообщение ---> {}", value);
      }
    }
    messageInteractor.saveMessage(kafkaMessageConverter.convert(values)
        .collect(Collectors.toList()));
    logger.info("Сообщения обработаны, смещение offset");
  }
}
