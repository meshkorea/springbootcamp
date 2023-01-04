package com.vroong.edge.controller;

import lombok.Data;

@Data
public class ProblemDetail {

  private String type = "bootcamp/problem-with-message";
  private String title = "알 수 없는 오류가 발생했습니다";
  private String detail = "알 수 없는 오류가 발생했습니다";
}
