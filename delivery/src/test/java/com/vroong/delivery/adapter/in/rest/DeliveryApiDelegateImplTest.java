package com.vroong.delivery.adapter.in.rest;

import com.vroong.delivery.config.Constants;
import com.vroong.delivery.rest.DeliveryApiController;
import com.vroong.delivery.rest.DeliveryApiDelegate;
import com.vroong.delivery.rest.DeliveryDto;
import com.vroong.delivery.support.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class DeliveryApiDelegateImplTest {

  private MockMvc mvc;

  private DeliveryDto deliveryDto;

  @Autowired
  private DeliveryApiDelegate deliveryApiDelegate;

  @BeforeEach
  void setup() throws IOException {
    this.mvc = MockMvcBuilders
        .standaloneSetup(new DeliveryApiController(deliveryApiDelegate))
        .addFilters(new CharacterEncodingFilter("utf-8", true))
        .build();

    deliveryDto = Fixtures.aDeliveryDto();
  }

  @Test
  @Disabled
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
                .content(TestUtils.convertObjectToString(deliveryDto))
        ).andDo(print());

    res.andExpect(status().isCreated())
            .andExpect(jsonPath("$.deliveryId").isNumber())
            .andExpect(jsonPath("$.orderId").isNumber())
            .andExpect(jsonPath("$.sender.name").value(deliveryDto.getSender().getName()))
            .andExpect(jsonPath("$.sender.phone").value(deliveryDto.getSender().getPhone()))
            .andExpect(jsonPath("$.sender.address").value(deliveryDto.getSender().getAddress()))
            .andExpect(jsonPath("$.receiver.name").value(deliveryDto.getReceiver().getName()))
            .andExpect(jsonPath("$.receiver.phone").value(deliveryDto.getReceiver().getPhone()))
            .andExpect(jsonPath("$.receiver.address").value(deliveryDto.getReceiver().getAddress()));
  }

  @Test
  @Disabled
  void getDelivery() throws Exception {
    final ResultActions res =
        mvc.perform(
            get("/api/deliveries/{deliveryId}", 1L)
                .characterEncoding(Constants.ENCODING)
        ).andDo(print());

    res.andExpect(status().isOk());
    res.andExpect(content().string(TestUtils.convertObjectToString(deliveryDto)));
  }

  @Test
  @Disabled
  void updateDelivery() throws Exception {
    final ResultActions res =
        mvc.perform(
            patch("/api/deliveries/{deliveryId}", 1L)
                .contentType(Constants.V1_MEDIA_TYPE)
                .characterEncoding(Constants.ENCODING)
                .content(TestUtils.convertObjectToString(deliveryDto))
        ).andDo(print());

    res.andExpect(status().isNoContent());
  }
}