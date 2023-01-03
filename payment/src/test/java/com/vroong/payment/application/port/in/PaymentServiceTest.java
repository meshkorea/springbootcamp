package com.vroong.payment.application.port.in;

import static com.vroong.payment.application.port.out.Fixtures.aCancelPaymentResponse;
import static com.vroong.payment.application.port.out.Fixtures.aRandomCheckoutPaymentResponse;
import static com.vroong.payment.application.port.out.Fixtures.anOrder;
import static com.vroong.payment.domain.Fixtures.aPayment;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vroong.order.rest.OrderApi;
import com.vroong.payment.application.PersistentEventCreator;
import com.vroong.payment.application.port.out.PaymentRepository;
import com.vroong.payment.application.port.out.rest.ThirdPartyApi;
import com.vroong.payment.domain.Payment;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
@Slf4j
class PaymentServiceTest {

  @MockBean PaymentRepository repository;
  @Autowired PaymentService service;
  @MockBean PersistentEventCreator eventCreator;
  @MockBean ThirdPartyApi pgApi;
  @MockBean OrderApi orderApi;

  ClientAndServer mockServer;

  @BeforeAll
  void setUp() throws JsonProcessingException {
    mockServer = ClientAndServer.startClientAndServer(65_535);
    ObjectMapper mapper = new ObjectMapper();

    new MockServerClient("localhost", 65_535)
        .when(request().withMethod("POST").withPath("/checkout"))
        .respond(
            request ->
                response()
                    .withHeader(new Header("Content-Type", "application/json"))
                    .withBody(mapper.writeValueAsString(aRandomCheckoutPaymentResponse())));

    new MockServerClient("localhost", 65_535)
        .when(request().withMethod("POST").withPath("/cancel"))
        .respond(
            response()
                .withHeader(new Header("Content-Type", "application/json"))
                .withBody(mapper.writeValueAsString(aCancelPaymentResponse())));
  }

  @Test
  void 체크아웃한다() {
    given(orderApi.getOrder(anyLong())).willReturn(anOrder());
    given(repository.save(any(Payment.class))).willReturn(aPayment());
    given(pgApi.checkout(any(Payment.class)))
        .willReturn(ResponseEntity.ok(aRandomCheckoutPaymentResponse()));
    willDoNothing().given(eventCreator).create(anyString(), any());

    service.checkout(aPayment());

    verify(orderApi, times(1)).getOrder(anyLong());
    verify(repository, times(2)).save(any(Payment.class));
    verify(pgApi, times(1)).checkout(any(Payment.class));
    verify(eventCreator, atLeastOnce()).create(anyString(), any());
  }

  @Test
  void 결제_목록을_조회한다() {
    given(repository.findByOrderId(anyLong())).willReturn(List.of(aPayment()));

    List<Payment> paymentList = service.getPaymentList(anyLong());

    verify(repository, times(1)).findByOrderId(anyLong());
    assertThat(paymentList.size()).isEqualTo(1);
  }

  @Test
  void 결제를_취소한다() {
    given(repository.findApprovedByOrderId(anyLong())).willReturn(Optional.of(aPayment()));
    given(pgApi.cancel(any(Payment.class))).willReturn(ResponseEntity.ok(aCancelPaymentResponse()));
    given(repository.save(any(Payment.class))).willReturn(aPayment());
    willDoNothing().given(eventCreator).create(anyString(), any());

    service.cancelPayment(anyLong());

    verify(repository, times(1)).findApprovedByOrderId(anyLong());
    verify(pgApi, times(1)).cancel(any(Payment.class));
    verify(repository, times(1)).save(any(Payment.class));
    verify(eventCreator, atLeastOnce()).create(anyString(), any());
  }

  @AfterAll
  void tearDown() {
    mockServer.stop();
  }
}
