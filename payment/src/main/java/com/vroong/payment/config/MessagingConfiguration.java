package com.vroong.payment.config;

import com.vroong.payment.application.port.out.ProducerChannel;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(value = {ProducerChannel.class})
public class MessagingConfiguration {
}