package com.vroong.order.adapter.in.rest;

import static com.vroong.order.config.Constants.V1_MEDIA_TYPE;
import static com.vroong.order.config.Constants.encoding;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.vroong.order.rest.OrderApiController;
import com.vroong.order.rest.OrderApiDelegate;
import com.vroong.order.support.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

@SpringBootTest
class OrderApiDelegateImplTest {

  MockMvc mvc;

  @Autowired
  OrderApiDelegate orderApiDelegate;


  @Test
  void createOrder() throws Exception {
    ResultActions res = mvc.perform(post("/api/orders")
            .contentType(V1_MEDIA_TYPE)
            .characterEncoding(encoding)
            .content(TestUtils.convertObjectToString(Fixture.aOrdersDto()))
        )
        .andDo(print());

    res.andExpect(status().is2xxSuccessful());
    res.andExpect(jsonPath("$.orderId").exists());
  }

  @Test
  void getOrder() throws Exception {
    ResultActions res = mvc.perform(get("/api/orders/{orderId}", 1L)
            .contentType(V1_MEDIA_TYPE)
            .characterEncoding(encoding)
        )
        .andDo(print());

    res.andExpect(status().is2xxSuccessful());
    res.andExpect(jsonPath("$.orderId").exists());
  }

  @Test
  void getOrders() throws Exception {
    ResultActions res = mvc.perform(get("/api/orders")
            .contentType(V1_MEDIA_TYPE)
            .characterEncoding(encoding)
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
            .characterEncoding(encoding)
            .content(TestUtils.convertObjectToString(Fixture.aOrdersDto()))
        )
        .andDo(print());

    res.andExpect(status().is2xxSuccessful());
  }

  @Test
  void cancelOrder() throws Exception {
    ResultActions res = mvc.perform(delete("/api/orders/{orderId}", 1L)
            .contentType(V1_MEDIA_TYPE)
            .characterEncoding(encoding)
        )
        .andDo(print());

    res.andExpect(status().is2xxSuccessful());
  }

  @BeforeEach
  void setUp() {
    this.mvc = MockMvcBuilders
        .standaloneSetup(new OrderApiController(orderApiDelegate))
        .addFilters(new CharacterEncodingFilter(encoding, true))
        .build();
  }
}