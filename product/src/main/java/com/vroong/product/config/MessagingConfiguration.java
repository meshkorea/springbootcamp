package com.vroong.product.config;

import com.vroong.product.application.port.out.ProducerChannel;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(value = {ProducerChannel.class})
public class MessagingConfiguration {
}