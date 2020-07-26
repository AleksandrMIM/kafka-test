package ru.mina.test.kafka.dto;

/**
 * Интерфейс для валидации данных
 * User: @AleksandrMIM
 * Date: 26.07.2020
 * Time: 19:49
 */
public interface Validatable {

  /**
   * Валидация данных
   *
   * @return true - валиден, иначе false
   */
  boolean validate();
}
