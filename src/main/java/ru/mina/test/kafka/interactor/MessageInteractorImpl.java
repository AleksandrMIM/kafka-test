package ru.mina.test.kafka.interactor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
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
@Slf4j
@RequiredArgsConstructor
public class MessageInteractorImpl implements MessageInteractor {

  private final MessageRepository messageRepository;

  @Override
  public void saveMessage(List<Message> messages) {
    if (messages != null && !messages.isEmpty()) {
      try {
        logger.info("Сохраняем сообщения в БД");
        messageRepository.addAll(messages);
        logger.info("Сообщения сохранены в БД");
      } catch (DataIntegrityViolationException e) {
        logger.warn("Нарушено ограничение целлостности при сохранении пачки записей в БД. Сохраняем сообщения по одному", e);
        if (messages.size() > 1) {
          for (Message message : messages) {
            try {
              logger.info("Сохраняем в БД сообщение {}", message.getId());
              messageRepository.add(message);
              logger.info("Сообщение {} сохранено в БД", message.getId());
            } catch (DataIntegrityViolationException ex) {
              logger.warn("Нарушено ограничение целлостности при сохранении записи по одной в БД", ex);
            }
          }
        }
      }
    }
  }
}
