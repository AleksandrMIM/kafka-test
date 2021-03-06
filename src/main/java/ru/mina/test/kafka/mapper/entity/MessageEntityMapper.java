package ru.mina.test.kafka.mapper.entity;

import org.mapstruct.Mapper;
import ru.mina.test.kafka.entity.MessageEntity;
import ru.mina.test.kafka.model.Message;

/**
 * User: @AleksandrMIM
 * Date: 22.07.2020
 * Time: 18:49
 */
@Mapper(componentModel = "spring")
public interface MessageEntityMapper {

  MessageEntity map(Message message);
}
