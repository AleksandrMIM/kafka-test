package ru.mina.test.kafka.repository;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.mina.test.kafka.dao.MessageDao;
import ru.mina.test.kafka.entity.MessageEntity;
import ru.mina.test.kafka.mapper.entity.MessageEntityMapper;
import ru.mina.test.kafka.model.Message;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Дефолтная реализация репозитория для работы с сообщениями
 * User: @AleksandrMIM
 * Date: 23.07.2020
 * Time: 12:20
 */
@Repository
@Transactional
@RequiredArgsConstructor
public class MessageRepositoryImpl implements MessageRepository {

  private final MessageDao messageDao;
  private final MessageEntityMapper entityMapper;

  @Override
  public void addAll(@NotNull Collection<Message> messages) {
    List<MessageEntity> messageEntities = messages
        .stream()
        .map(entityMapper::map)
        .collect(Collectors.toList());
    messageDao.saveAll(messageEntities);
  }
}
