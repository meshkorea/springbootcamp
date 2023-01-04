package com.vroong.payment.application;

import static com.vroong.payment.config.Constants.PRODUCER_CHANNEL;
import static com.vroong.payment.domain.Fixtures.aPayment;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vroong.payment.domain.Payment;
import com.vroong.payment.domain.PersistentEvent;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;

@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
class StreamBridgeMessageProducerTest {

  @Autowired OutputDestination targetDestination;
  @Autowired StreamBridgeMessageProducer producer;
  @Autowired ObjectMapper objectMapper;

  @Test
  void 발행된_메시지를_수신할_수_있다() throws JsonProcessingException {
    final Payment payment = aPayment();
    producer.produce(
        PersistentEvent.newInstance(
            "PAYMENT_" + payment.getStatus(),
            UUID.randomUUID(),
            objectMapper.writeValueAsString(payment)));
    Message<byte[]> result = targetDestination.receive(100, PRODUCER_CHANNEL + ".destination");
    assertThat(result).isNotNull();
    assertThat(new String(result.getPayload())).isEqualTo(objectMapper.writeValueAsString(payment));
  }
}
