package ru.mina.test.kafka.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * User: @AleksandrMIM
 * Date: 21.07.2020
 * Time: 23:46
 */
@Entity
@Table(name = "message")
@Getter
@Setter
public class MessageEntity extends Persistent {
  /**
   * Содержимое сообщения
   */
  @Column(name = "payload")
  private String payload;
}