package ru.mina.test.kafka.interactor;

import ru.mina.test.kafka.model.Message;

import java.util.List;

/**
 * Описание процесса сохранения сообщений
 * User: @AleksandrMIM
 * Date: 23.07.2020
 * Time: 12:13
 */
public interface MessageInteractor {

  /**
   * Сохранение сообщений
   *
   * @param message сообщение
   */
  void saveMessage(List<Message> message);
}
