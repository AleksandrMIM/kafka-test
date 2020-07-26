package ru.mina.test.kafka.converter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import ru.mina.test.kafka.deserializer.KafkaMessageDeserializer;
import ru.mina.test.kafka.dto.KafkaGroupMessage;
import ru.mina.test.kafka.dto.KafkaMessage;
import ru.mina.test.kafka.mapper.dto.MessageDtoMapper;
import ru.mina.test.kafka.model.Message;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * User: @AleksandrMIM
 * Date: 26.07.2020
 * Time: 17:04
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaMessageConverterImpl implements KafkaMessageConverter {

  private final KafkaMessageDeserializer deserializer;
  private final MessageDtoMapper dtoMapper;

  @Override
  public @NotNull Stream<Message> convert(Collection<String> values) {
    return values == null || values.isEmpty() ? Stream.empty() : values
        .stream()
        .map(deserializer::deserialize)
        .flatMap(Optional::stream)
        .filter(KafkaGroupMessage::validate)
        .map(KafkaGroupMessage::getMessages)
        .flatMap(Collection::stream)
        .filter(KafkaMessage::validate)
        .map(dtoMapper::map);
  }
}
