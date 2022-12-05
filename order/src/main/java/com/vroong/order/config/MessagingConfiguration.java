package com.vroong.order.config;

import com.vroong.order.application.port.out.ProducerChannel;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(value = {ProducerChannel.class})
public class MessagingConfiguration {
}