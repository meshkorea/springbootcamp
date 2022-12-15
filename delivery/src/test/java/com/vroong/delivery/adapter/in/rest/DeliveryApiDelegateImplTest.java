package com.vroong.delivery.adapter.in.rest;

import com.vroong.delivery.adapter.in.rest.mapper.DeliveryFactory;
import com.vroong.delivery.application.port.in.DeliveryService;
import com.vroong.delivery.config.Constants;
import com.vroong.delivery.domain.Delivery;
import com.vroong.delivery.domain.DeliveryStatus;
import com.vroong.delivery.domain.UserInfo;
import com.vroong.delivery.rest.DeliveryApiController;
import com.vroong.delivery.rest.DeliveryApiDelegate;
import com.vroong.delivery.rest.DeliveryDto;
import com.vroong.delivery.rest.UserInfoDto;
import com.vroong.delivery.support.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class DeliveryApiDelegateImplTest {

  private MockMvc mvc;

  private DeliveryDto deliveryDto;

  @Autowired
  private DeliveryApiDelegate deliveryApiDelegate;

  @Autowired
  private DeliveryService deliveryService;

  @Autowired
  private DeliveryFactory deliveryFactory;

  @BeforeEach
  void setup() throws IOException {
    this.mvc = MockMvcBuilders
        .standaloneSetup(new DeliveryApiController(deliveryApiDelegate))
        .addFilters(new CharacterEncodingFilter("utf-8", true))
        .build();

    deliveryDto = Fixtures.aDeliveryDto();
  }

  @Test
  void cancelDelivery() throws Exception {
    Delivery delivery = deliveryService.createDelivery(deliveryFactory.createFrom(deliveryDto));

    final ResultActions res =
        mvc.perform(
            delete("/api/deliveries/{deliveryId}", delivery.getId())
                .characterEncoding(Constants.ENCODING)
        ).andDo(print());

    res.andExpect(status().isNoContent());

    Delivery resultDelivery = deliveryService.getDelivery(delivery.getId());
    assertEquals(resultDelivery.getStatus(), DeliveryStatus.CANCELED);
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

    // TODO status check
    res.andExpect(status().isCreated())
            .andExpect(jsonPath("deliveryId").isNumber())
            .andExpect(jsonPath("orderId").value(deliveryDto.getOrderId()))
            .andExpect(jsonPath("traceNumber").hasJsonPath())
            .andExpect(jsonPath("sender.name").value(deliveryDto.getSender().getName()))
            .andExpect(jsonPath("sender.phone").value(deliveryDto.getSender().getPhone()))
            .andExpect(jsonPath("sender.address").value(deliveryDto.getSender().getAddress()))
            .andExpect(jsonPath("receiver.name").value(deliveryDto.getReceiver().getName()))
            .andExpect(jsonPath("receiver.phone").value(deliveryDto.getReceiver().getPhone()))
            .andExpect(jsonPath("receiver.address").value(deliveryDto.getReceiver().getAddress()))
            .andExpect(jsonPath("currentLocation").hasJsonPath());
  }

  @Test
  void getDelivery() throws Exception {
    Delivery delivery = deliveryService.createDelivery(deliveryFactory.createFrom(deliveryDto));

    final ResultActions res =
        mvc.perform(
            get("/api/deliveries/{deliveryId}", delivery.getId())
                .characterEncoding(Constants.ENCODING)
        ).andDo(print());

    // TODO check status
    res.andExpect(status().isOk())
            .andExpect(jsonPath("deliveryId").value(delivery.getId()))
            .andExpect(jsonPath("orderId").value(delivery.getOrderId()))
            .andExpect(jsonPath("traceNumber").hasJsonPath())
            .andExpect(jsonPath("sender.name").value(delivery.getDeliveryUserInfo().getSender().getName()))
            .andExpect(jsonPath("sender.phone").value(delivery.getDeliveryUserInfo().getSender().getPhone()))
            .andExpect(jsonPath("sender.address").value(delivery.getDeliveryUserInfo().getSender().getAddress()))
            .andExpect(jsonPath("receiver.name").value(delivery.getDeliveryUserInfo().getReceiver().getName()))
            .andExpect(jsonPath("receiver.phone").value(delivery.getDeliveryUserInfo().getReceiver().getPhone()))
            .andExpect(jsonPath("receiver.address").value(delivery.getDeliveryUserInfo().getReceiver().getAddress()))
            .andExpect(jsonPath("currentLocation").hasJsonPath());
  }

  @Test
  void updateDelivery() throws Exception {
    Delivery delivery = deliveryService.createDelivery(deliveryFactory.createFrom(deliveryDto));

    final UserInfoDto senderDto = new UserInfoDto()
            .name("Sender User")
            .phone("01012123434")
            .address("서울 종로구 효자로 12");
    final UserInfoDto receiverDto = new UserInfoDto()
            .name("Receiver User")
            .phone("01056567878")
            .address("서울 종로구 세종대로 209");

    DeliveryDto newDeliveryDto = new DeliveryDto()
            .sender(senderDto)
            .receiver(receiverDto);

    final ResultActions res =
        mvc.perform(
            patch("/api/deliveries/{deliveryId}", delivery.getId())
                .contentType(Constants.V1_MEDIA_TYPE)
                .characterEncoding(Constants.ENCODING)
                .content(TestUtils.convertObjectToString(newDeliveryDto))
        ).andDo(print());

    res.andExpect(status().isNoContent());

    Delivery changedDelivery = deliveryService.getDelivery(delivery.getId());

    UserInfo sender = changedDelivery.getDeliveryUserInfo().getSender();
    UserInfo receiver = changedDelivery.getDeliveryUserInfo().getReceiver();

    res.andExpect(status().isNoContent());
    assertEquals(newDeliveryDto.getSender().getName(), sender.getName());
    assertEquals(newDeliveryDto.getSender().getPhone(), sender.getPhone());
    assertEquals(newDeliveryDto.getSender().getAddress(), sender.getAddress());
    assertEquals(newDeliveryDto.getReceiver().getName(), receiver.getName());
    assertEquals(newDeliveryDto.getReceiver().getPhone(), receiver.getPhone());
    assertEquals(newDeliveryDto.getReceiver().getAddress(), receiver.getAddress());
  }
}