package com.vroong.edge.controller;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.deleteRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.patch;
import static com.github.tomakehurst.wiremock.client.WireMock.patchRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.vroong.edge.EdgeTestConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;
import reactor.core.publisher.Mono;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = EdgeTestConfiguration.class)
@DisplayName("프록시 테스트")
class ProxyLocatorTest {

  @Autowired
  WebTestClient webTestClient;

  WireMockServer orderMockServer = new WireMockServer(10001);
  WireMockServer productMockServer = new WireMockServer(10002);
  WireMockServer deliveryMockServer = new WireMockServer(10003);
  WireMockServer paymentMockServer = new WireMockServer(10004);

  @Test
  @WithMockUser
  void 제품_목록_조회() {
    ResponseSpec res = webTestClient
        .get()
        .uri("/bootcamp/products")
        .exchange();

    res.expectStatus().is2xxSuccessful();
    productMockServer.verify(getRequestedFor(urlEqualTo("/api/products")));
  }

  @Test
  @WithMockUser
  void 주문_생성() {
    ResponseSpec res = webTestClient
        .post()
        .uri("/bootcamp/orders")
        .body(Mono.just(""), String.class)
        .exchange();

    res.expectStatus().is2xxSuccessful();
    orderMockServer.verify(postRequestedFor(urlEqualTo("/api/orders")));
  }

  @Test
  @WithMockUser
  void 체크아웃() {
    ResponseSpec res = webTestClient
        .post()
        .uri("/bootcamp/payments")
        .body(Mono.just(""), String.class)
        .exchange();

    res.expectStatus().is2xxSuccessful();
    paymentMockServer.verify(postRequestedFor(urlEqualTo("/api/payments")));
  }

  @Test
  @WithMockUser
  void 주문_목록_조회() {
    ResponseSpec res = webTestClient
        .get()
        .uri("/bootcamp/orders")
        .exchange();

    res.expectStatus().is2xxSuccessful();
    orderMockServer.verify(getRequestedFor(urlEqualTo("/api/orders")));
  }

  @Test
  @WithMockUser
  void 주문_변경() {
    ResponseSpec res = webTestClient
        .patch()
        .uri("/bootcamp/orders/1")
        .body(Mono.just(""), String.class)
        .exchange();

    res.expectStatus().is2xxSuccessful();
    orderMockServer.verify(patchRequestedFor(urlEqualTo("/api/orders/1")));
  }

  @Test
  @WithMockUser
  void 주문_취소() {
    ResponseSpec res = webTestClient
        .delete()
        .uri("/bootcamp/orders/1")
        .exchange();

    res.expectStatus().is2xxSuccessful();
    orderMockServer.verify(deleteRequestedFor(urlEqualTo("/api/orders/1")));
  }

  @Test
  @WithMockUser
  void 배송_상태_조회() {
    ResponseSpec res = webTestClient
        .get()
        .uri("/bootcamp/deliveries/1")
        .exchange();

    res.expectStatus().is2xxSuccessful();
    deliveryMockServer.verify(getRequestedFor(urlEqualTo("/api/deliveries/1")));
  }

  @BeforeAll
  void setUp() {
    orderMockServer.givenThat(get(urlPathMatching("/api/orders"))
        .willReturn(aResponse()
            .withStatus(HttpStatus.OK.value()))
    );

    orderMockServer.givenThat(post(urlPathMatching("/api/orders"))
        .willReturn(aResponse()
            .withStatus(HttpStatus.OK.value()))
    );

    orderMockServer.givenThat(get(urlPathMatching("/api/orders/1"))
        .willReturn(aResponse()
            .withStatus(HttpStatus.OK.value()))
    );

    orderMockServer.givenThat(patch(urlPathMatching("/api/orders/1"))
        .willReturn(aResponse()
            .withStatus(HttpStatus.OK.value()))
    );

    orderMockServer.givenThat(delete(urlPathMatching("/api/orders/1"))
        .willReturn(aResponse()
            .withStatus(HttpStatus.OK.value()))
    );

    productMockServer.givenThat(get(urlPathMatching("/api/products"))
        .willReturn(aResponse()
            .withStatus(HttpStatus.OK.value()))
    );

    paymentMockServer.givenThat(post(urlPathMatching("/api/payments"))
        .willReturn(aResponse()
            .withStatus(HttpStatus.OK.value()))
    );

    paymentMockServer.givenThat(get(urlPathMatching("/api/payments/1"))
        .willReturn(aResponse()
            .withStatus(HttpStatus.OK.value()))
    );

    deliveryMockServer.givenThat(get(urlPathMatching("/api/deliveries/1"))
        .willReturn(aResponse()
            .withStatus(HttpStatus.OK.value()))
    );

    orderMockServer.start();
    productMockServer.start();
    paymentMockServer.start();
    deliveryMockServer.start();
  }

  @AfterAll
  void shutDown() {
    orderMockServer.shutdown();
    productMockServer.shutdown();
    paymentMockServer.shutdown();
    deliveryMockServer.shutdown();
  }
}
