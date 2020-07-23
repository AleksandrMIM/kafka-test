package ru.mina.test.kafka.mapper.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.mina.test.kafka.dto.KafkaMessage;
import ru.mina.test.kafka.model.Message;

/**
 * User: @AleksandrMIM
 * Date: 22.07.2020
 * Time: 18:49
 */
@Mapper(componentModel = "spring")
public interface MessageDtoMapper {

  @Mapping(target = "id", source = "messageId")
  Message map(KafkaMessage kafkaMessage);
}
