package com.vroong.order.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

  private String version = "0.0.1.SNAPSHOT";

  private Scheduler scheduler = new Scheduler();

  private Kafka kafka = new Kafka();

  @Getter
  @Setter
  public static class Scheduler {

    private Boolean enabled;
  }

  @Getter
  @Setter
  public static class Kafka {

    private Topic topic;

    @Getter
    @Setter
    public static class Topic {

      private String delivery;
      private String payment;
    }
  }
}
