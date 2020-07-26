package ru.mina.test.kafka.mapper;

import org.springframework.boot.test.mock.mockito.MockBean;
import ru.mina.test.kafka.dao.MessageDao;

/**
 * User: @AleksandrMIM
 * Date: 26.07.2020
 * Time: 19:17
 */
public class AbstractTest {

  @MockBean
  protected MessageDao messageDao;
}
