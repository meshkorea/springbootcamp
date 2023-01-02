package com.vroong.edge.config;

public class Constants {

  public static final String VROONG_MEDIA_TYPE = "application/vnd.vroong.private.v1+json";
  public static final String ZERO_UUID = "00000000-0000-0000-0000-000000000000";

  public static class JwtKey {
    public static final String USER_ID_CLAIM = "user_name";
    public static final String DISPLAY_NAME_CLAIM = "display_name";
    public static final String AUTHORITIES_CLAIM = "authorities";

  }
}
