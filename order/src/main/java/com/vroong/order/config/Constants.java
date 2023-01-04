package com.vroong.order.config;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Constants {

  public static final String PROJECT_NAME = "order";
  public static final String SYSTEM_ACCOUNT = "system";
  public static final String UNKNOWN_USER_ID = "00000000-0000-0000-0000-000000000000";
  public static final String V1_MEDIA_TYPE = "application/vnd.vroong.private.v1+json";
  public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

  public static final String TIMEZONE_SEOUL = "UTC+9";
  public static final int TIMEZONE_SEOUL_HOURS = 9;

  public static class Profile {

    public static final String TEST_PROFILE = "test";
    public static final String LOCAL_PROFILE = "local";
    public static final String DEV_PROFILE = "dev";
    public static final String QA_PROFILE = "qa";
    public static final String PROD_PROFILE = "prod";
  }

  public static class HeaderKey {

    public static final String B3_TRACE_ID = "X-B3-TraceId";
    public static final String B3_SPAN_ID = "X-B3-SpanId";
    public static final String B3_PARENT_ID = "X-B3-ParentSpanId";
    public static final String B3_SAMPLED = "X-B3-Sampled";
    public static final String B3_FLAGS = "X-B3-Flags";
  }

  public static final String PRODUCER_CHANNEL = "messageProducer-out-0";
  public static final String CONSUMER_CHANNEL = "messageConsumer-in-0";

  public static final String PAYMENT_PROJECT_NAME = "payment";
  public static final String DELIVERY_PROJECT_NAME = "delivery";

  /**
   * MessageSchema spec: @see https://wiki.mm.meshkorea.net/display/MES/Message+Schema
   */
  public static class MessageKey {

    public static final String ID = "messageId";
    public static final String TYPE = "messageType";
    public static final String VERSION = "messageVersion";
    public static final String SOURCE = "messageSource";
    public static final String RESOURCE = "resource";
    public static final String PARTITION_KEY = "partitionKey";
  }

  public static class MessagePolicy {

    public static final Long DEFAULT_TIMEOUT = 5000L; // milliseconds
  }

  public static class LogKey {

    public static final String HTTP_INBOUND_LOGGER = "com.vroong.order.http.api";
    public static final String HTTP_OUTBOUND_LOGGER = "com.vroong.order.http.external";
    public static final String REQUEST = "request";
    public static final String RESPONSE = "response";
    public static final String USER_NAME = "userName";
    public static final String STATUS = "status";
    public static final String ENDPOINT = "endpoint";
    public static final String HEADERS = "headers";
    public static final String BODY = "body";
  }

  public static class JwtKey {

    public static final String USER_ID_CLAIM = "user_name";
  }

  public static class OrderNotFound {

    public static final String MESSAGE = "존재하지 않는 주문입니다";
  }

  public static class OrdererNotMatched {

    public static final String CANCEL_ORDER_MESSAGE = "자신의 주문만 취소할 수 있습니다";
    public static final String UPDATE_ORDER_MESSAGE = "자신의 주문만 변경할 수 있습니다";
    public static final String GET_ORDER_MESSAGE = "자신의 주문만 조회할 수 있습니다";
  }

  public static class ChangeOrderStatus {

    public static final String CANCEL_ORDER_MESSAGE = "취소 가능한 상태가 아닙니다";
    public static final String UPDATE_ORDER_MESSAGE = "변경 가능한 상태가 아닙니다";
  }

  private Constants() {
  }
}
