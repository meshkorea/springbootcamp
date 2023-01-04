package com.vroong.edge.controller;

import static com.vroong.edge.config.Constants.VROONG_MEDIA_TYPE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.vroong.edge.application.OrderDetailService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;
import reactor.core.publisher.Mono;

@WebFluxTest(OrderDetailController.class)
@DisplayName("애그리거트 테스트")
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
        .header(HttpHeaders.CONTENT_TYPE, VROONG_MEDIA_TYPE)
        .exchange();

    res.expectStatus().is2xxSuccessful();
  }
}
