package ru.mina.test.kafka.interactor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mina.test.kafka.model.Message;
import ru.mina.test.kafka.repository.MessageRepository;

import java.util.List;

/**
 * Реализация описания процесса сохранения сообщений в БД
 * User: @AleksandrMIM
 * Date: 23.07.2020
 * Time: 12:16
 */
@Service
@RequiredArgsConstructor
public class MessageInteractorImpl implements MessageInteractor {

  private final MessageRepository messageRepository;

  @Override
  public void saveMessage(List<Message> message) {
    messageRepository.addAll(message);
  }
}
