package com.vroong.delivery.adapter.in.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.vroong.delivery.config.Constants;
import com.vroong.delivery.rest.DeliveryApiController;
import com.vroong.delivery.rest.DeliveryApiDelegate;
import com.vroong.delivery.support.TestUtils;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

@SpringBootTest
class DeliveryApiDelegateImplTest {

  private MockMvc mvc;

  private String deliveryDtoString;

  @Autowired
  private DeliveryApiDelegate deliveryApiDelegate;

  @BeforeEach
  void setup() throws IOException {
    this.mvc = MockMvcBuilders
        .standaloneSetup(new DeliveryApiController(deliveryApiDelegate))
        .addFilters(new CharacterEncodingFilter("utf-8", true))
        .build();
    deliveryDtoString = TestUtils.convertObjectToString(Fixtures.aDeliveryDto());
  }

  @Test
  void cancelDelivery() throws Exception {
    final ResultActions res =
        mvc.perform(
            delete("/api/deliveries/{deliveryId}", 1L)
                .characterEncoding(Constants.ENCODING)
        ).andDo(print());

    res.andExpect(status().isNoContent());
  }

  @Test
  void createDelivery() throws Exception {
    final ResultActions res =
        mvc.perform(
            post("/api/deliveries")
                .contentType(Constants.V1_MEDIA_TYPE)
                .characterEncoding(Constants.ENCODING)
                .content(deliveryDtoString)
        ).andDo(print());

    res.andExpect(status().isCreated());
    res.andExpect(content().string(deliveryDtoString));
  }

  @Test
  void getDelivery() throws Exception {
    final ResultActions res =
        mvc.perform(
            get("/api/deliveries/{deliveryId}", 1L)
                .characterEncoding(Constants.ENCODING)
        ).andDo(print());

    res.andExpect(status().isOk());
    res.andExpect(content().string(deliveryDtoString));
  }

  @Test
  void updateDelivery() throws Exception {
    final ResultActions res =
        mvc.perform(
            patch("/api/deliveries/{deliveryId}", 1L)
                .contentType(Constants.V1_MEDIA_TYPE)
                .characterEncoding(Constants.ENCODING)
                .content(deliveryDtoString)
        ).andDo(print());

    res.andExpect(status().isNoContent());
  }
}