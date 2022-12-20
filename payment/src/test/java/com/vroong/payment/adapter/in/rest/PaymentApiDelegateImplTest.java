package com.vroong.payment.adapter.in.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.vroong.payment.config.Constants;
import com.vroong.payment.rest.PaymentApiController;
import com.vroong.payment.rest.PaymentApiDelegate;
import com.vroong.payment.support.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

@SpringBootTest
@WithMockUser
class PaymentApiDelegateImplTest {

  private MockMvc mvc;

  @Autowired private PaymentApiDelegate apiDelegate;

  @BeforeEach
  void setup() {
    this.mvc =
        MockMvcBuilders.standaloneSetup(new PaymentApiController(apiDelegate))
            .addFilters(new CharacterEncodingFilter("utf-8", true))
            .build();
  }

  @Test
  void createPayment() throws Exception {
    final ResultActions res =
        mvc.perform(
                post("/api/payments")
                    .contentType(Constants.V1_MEDIA_TYPE)
                    .content(TestUtils.convertObjectToString(Fixtures.aPaymentDto()))
                    .characterEncoding("utf-8"))
            .andDo(print());

    res.andExpect(status().is2xxSuccessful());
  }

  @Test
  void getPaymentList() throws Exception {
    final ResultActions res =
        mvc.perform(
                get("/api/payments/{orderId}", 1L)
                    .accept(Constants.V1_MEDIA_TYPE)
                    .characterEncoding("utf-8"))
            .andDo(print());

    res.andExpect(status().is2xxSuccessful());
  }
}
