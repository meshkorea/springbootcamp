package com.vroong.payment.application.port.in;

import static com.vroong.payment.domain.Fixtures.aPayment;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vroong.payment.application.port.out.Fixtures;
import com.vroong.payment.application.port.out.PaymentRepository;
import com.vroong.payment.domain.Payment;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@Slf4j
class PaymentServiceTest {

  @MockBean PaymentRepository repository;
  @Autowired PaymentService service;
  ClientAndServer mockServer;

  @BeforeEach
  void setUp() throws JsonProcessingException {
    mockServer = ClientAndServer.startClientAndServer(65_535);
    ObjectMapper mapper = new ObjectMapper();

    new MockServerClient("localhost", 65_535)
        .when(request().withMethod("POST").withPath("/checkout"))
        .respond(
            request -> response()
                .withHeader(new Header("Content-Type", "application/json"))
                .withBody(mapper.writeValueAsString(Fixtures.aRandomCheckoutPaymentResponse()))
        );

    new MockServerClient("localhost", 65_535)
        .when(request().withMethod("POST").withPath("/cancel"))
        .respond(
            response()
                .withHeader(new Header("Content-Type", "application/json"))
                .withBody(mapper.writeValueAsString(Fixtures.aCancelPaymentResponse())));
  }

  @Test
  void checkout() {
    when(repository.save(any(Payment.class))).thenReturn(aPayment());

    service.checkout(aPayment());
  }

  @Test
  void getPaymentList() {
    when(repository.findByOrderId(anyLong())).thenReturn(List.of(aPayment()));

    List<Payment> entityList = service.getPaymentList(1L);

    log.info("{}", entityList);
  }

  @Test
  void cancelPayment() {
    when(repository.findApprovedByOrderId(anyLong())).thenReturn(Optional.of(aPayment()));

    service.cancelPayment(1L);
  }

  @AfterEach
  void tearDown() {
    mockServer.stop();
  }
}
