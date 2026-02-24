package com.aditi_midterm.financemanager.transaction.dto;

import lombok.Data;

@Data
public class Pagination {

  private Integer page = 0;
  private Integer size = 10;

  private Long total;
  private Integer totalPage;
}
