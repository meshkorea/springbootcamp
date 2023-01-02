package com.vroong.edge.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.vroong.edge.application.OrderDetailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;
import reactor.core.publisher.Mono;

@WebFluxTest(OrderDetailController.class)
class OrderDetailControllerTest {

  @Autowired
  WebTestClient webTestClient;

  @MockBean
  OrderDetailService service;

  @Test
  @WithMockUser
  void 주문_상세_조회() {
    given(service.getOrderDetail(any()))
        .willReturn(Mono.just(new OrderDetailDto(null, null)));

    ResponseSpec res = webTestClient.get()
        .uri("/bootcamp/order-detail/1")
        .exchange();

    res.expectStatus().is2xxSuccessful();
  }
}
