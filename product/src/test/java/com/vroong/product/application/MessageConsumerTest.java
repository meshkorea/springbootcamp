package com.vroong.product.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

import com.vroong.product.adapter.in.rest.Fixtures;
import com.vroong.product.application.port.out.OrderTemplate;
import com.vroong.product.application.port.out.ProductRepository;
import com.vroong.product.domain.Product;
import javax.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
class MessageConsumerTest {

  @Autowired
  private MessageConsumer messageConsumer;

  @Autowired
  private ProductService productService;

  @Autowired
  private ProductRepository productRepository;

  @MockBean
  private OrderTemplate orderTemplate;

  @MockBean
  private PersistentEventCreator eventCreator;

  @Test
  @Rollback
  void 주문이_됐을_때_재고_차감() {
    //given
    Product product = productService.createProduct(Fixtures.aProductDto());

    given(orderTemplate.getOrderByOrderId(any()))
        .willReturn(Fixtures.Order(product.getProductId(), 1));

    //when
    //주문 생성
    messageConsumer.onReceiveOrderCreated(Fixtures.anOrderEvent());

    //then
    Assertions.assertThat(productRepository.findById(product.getProductId()).get().getInventory())
        .isEqualTo(0);
  }

  @Test
  @Rollback
  void 주문이_취소_됐을_때_취소된_수량_만큼_재고_증가() {
    //given
    Product product = productService.createProduct(Fixtures.aProductDto());

    given(orderTemplate.getOrderByOrderId(any()))
        .willReturn(Fixtures.Order(product.getProductId(), 1));

    //when
    //주문이 취소 됨
    messageConsumer.onReceiveOrderCanceled(Fixtures.anOrderEvent());

    //then
    Assertions.assertThat(productRepository.findById(product.getProductId()).get().getInventory())
        .isEqualTo(2);
  }

  @BeforeEach
  public void setUp() {
    doNothing().when(eventCreator).create(any(),any());
  }
}
