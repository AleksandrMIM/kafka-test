package ru.mina.test.kafka.repository;

import org.jetbrains.annotations.NotNull;
import ru.mina.test.kafka.model.Message;

import java.util.Collection;

/**
 * Репозиторий для работы с сообщениями
 * User: @AleksandrMIM
 * Date: 22.07.2020
 * Time: 18:58
 */
public interface MessageRepository {

  /**
   * Добавление элементов в репозиторий
   *
   * @param messages сообщения
   */
  void addAll(@NotNull Collection<Message> messages);
}
