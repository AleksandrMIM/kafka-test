package ru.mina.test.kafka.model;

import lombok.Data;

/**
 * Сообщение
 * User: @AleksandrMIM
 * Date: 21.07.2020
 * Time: 23:41
 */
@Data
public class Message {
  /**
   * Идентификатор сообщения
   */
  private int id;
  /**
   * Содержимое сообщения
   */
  private String payload;
}
