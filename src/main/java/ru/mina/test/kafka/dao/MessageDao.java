package ru.mina.test.kafka.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.mina.test.kafka.entity.MessageEntity;

/**
 * Репозиторий для сохранения сообщений в БД
 * User: @AleksandrMIM
 * Date: 22.07.2020
 * Time: 0:07
 */
@Repository
public interface MessageDao extends CrudRepository<MessageEntity, Long> {
}
