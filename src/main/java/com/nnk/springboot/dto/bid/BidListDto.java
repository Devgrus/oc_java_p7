package com.nnk.springboot.dto.bid;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
public class BidListDto {
    private Integer id;
    private String account;
    private String type;
    private BigDecimal bidQuantity;
}
