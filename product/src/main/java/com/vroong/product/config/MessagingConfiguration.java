package com.vroong.product.config;

import com.vroong.product.application.port.in.ConsumerChannel;
import com.vroong.product.application.port.out.ProducerChannel;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(value = {ProducerChannel.class, ConsumerChannel.class})
public class MessagingConfiguration {
}