package ru.mina.test.kafka.consumer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.BatchAcknowledgingConsumerAwareMessageListener;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import ru.mina.test.kafka.annotation.ModelTest;
import ru.mina.test.kafka.mapper.AbstractTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * User: @AleksandrMIM
 * Date: 27.07.2020
 * Time: 21:17
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@ModelTest
@EmbeddedKafka(partitions = 1, topics = KafkaConsumerTest.TEST_TOPIC, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
public class KafkaConsumerTest extends AbstractTest {

  public static final String TEST_TOPIC = "testTopic";

  @Autowired
  private EmbeddedKafkaBroker embeddedKafkaBroker;
  @Autowired
  private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;

  @BeforeEach
  public void setUp() {
    for (MessageListenerContainer messageListenerContainer : kafkaListenerEndpointRegistry.getListenerContainers()) {
      ContainerTestUtils.waitForAssignment(messageListenerContainer, embeddedKafkaBroker.getPartitionsPerTopic());
    }
  }

  @Test
  public void send_emptyMessage_successResult() throws InterruptedException {
    checkKafka("{}");
  }

  @Test
  public void send_wrongMessage_successResult() throws InterruptedException {
    checkKafka("{5}");
  }

  private void checkKafka(String message) throws InterruptedException {
    CountDownLatch latch = new CountDownLatch(1);
    proxyKafkaListener(latch);
    kafkaTemplate.send(TEST_TOPIC, message);
    assertTrue(latch.await(2, TimeUnit.SECONDS));
  }

  @SuppressWarnings("unchecked")
  private void proxyKafkaListener(CountDownLatch latch) {
    var container = (ConcurrentMessageListenerContainer<?, ?>) kafkaListenerEndpointRegistry
        .getListenerContainer("batch-listener");
    var messageListener = (BatchAcknowledgingConsumerAwareMessageListener<String, String>) container
        .getContainerProperties().getMessageListener();

    container.stop();
    container.getContainerProperties()
        .setMessageListener((BatchAcknowledgingConsumerAwareMessageListener<String, String>) (data, acknowledgment, consumer) -> {
          messageListener.onMessage(data, acknowledgment, consumer);
          latch.countDown();
        });
    container.start();
  }

  @TestConfiguration
  public static class TestConfig {

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Bean
    public ProducerFactory<String, String> producerFactory() {
      return new DefaultKafkaProducerFactory<>(KafkaTestUtils.producerProps(embeddedKafkaBroker));
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
      KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(producerFactory());
      kafkaTemplate.setDefaultTopic(TEST_TOPIC);
      return kafkaTemplate;
    }
  }
}
