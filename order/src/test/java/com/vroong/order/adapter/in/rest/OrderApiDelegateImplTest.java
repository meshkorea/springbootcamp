package com.vroong.order.adapter.in.rest;

import com.vroong.order.application.port.in.OrderUsecase;
import com.vroong.order.domain.Orderer;
import com.vroong.order.domain.Receiver;
import com.vroong.order.rest.OrderApiController;
import com.vroong.order.rest.OrderApiDelegate;
import com.vroong.order.support.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import static com.vroong.order.config.Constants.ENCODING;
import static com.vroong.order.config.Constants.V1_MEDIA_TYPE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class OrderApiDelegateImplTest {

  MockMvc mvc;

  @Autowired
  OrderApiDelegate orderApiDelegate;

  @MockBean
  OrderUsecase orderUsecase;

  @Test
  @WithAnonymousUser
  void createOrder() throws Exception {
    given(orderUsecase.createOrder(any(Orderer.class), any(Receiver.class), any()))
        .willReturn(Fixture.aOrder());

    ResultActions res = mvc.perform(post("/api/orders")
            .contentType(V1_MEDIA_TYPE)
            .characterEncoding(ENCODING)
            .content(TestUtils.convertObjectToString(Fixture.aOrderDto()))
        )
        .andDo(print());

    res.andExpect(status().is2xxSuccessful());
    res.andExpect(jsonPath("$.orderId").exists());
  }

  @Test
  void getOrder() throws Exception {
    Long givenOrderId = 1L;
    given(orderUsecase.getOrder(givenOrderId)).willReturn(Fixture.aOrder());

    ResultActions res = mvc.perform(get("/api/orders/{orderId}", givenOrderId)
            .contentType(V1_MEDIA_TYPE)
            .characterEncoding(ENCODING)
        )
        .andDo(print());

    res.andExpect(status().is2xxSuccessful());
    res.andExpect(jsonPath("$.orderId").exists());
  }

  @Test
  void getOrderList() throws Exception {
    ResultActions res = mvc.perform(get("/api/orders")
            .contentType(V1_MEDIA_TYPE)
            .characterEncoding(ENCODING)
        )
        .andDo(print());

    res.andExpect(status().is2xxSuccessful());
    res.andExpect(jsonPath("$.data").isArray());
    res.andExpect(jsonPath("$.page").exists());
  }

  @Test
  void updateOrder() throws Exception {
    ResultActions res = mvc.perform(patch("/api/orders/{orderId}", 1L)
            .contentType(V1_MEDIA_TYPE)
            .characterEncoding(ENCODING)
            .content(TestUtils.convertObjectToString(Fixture.aOrderListDto()))
        )
        .andDo(print());

    res.andExpect(status().is2xxSuccessful());
  }

  @Test
  void cancelOrder() throws Exception {
    ResultActions res = mvc.perform(delete("/api/orders/{orderId}", 1L)
            .contentType(V1_MEDIA_TYPE)
            .characterEncoding(ENCODING)
        )
        .andDo(print());

    res.andExpect(status().is2xxSuccessful());
  }

  @BeforeEach
  void setUp() {
    this.mvc = MockMvcBuilders
        .standaloneSetup(new OrderApiController(orderApiDelegate))
        .addFilters(new CharacterEncodingFilter(ENCODING, true))
        .build();
  }
}
