package com.nnk.springboot.dto.trade;

import lombok.*;

@Getter
@Builder
public class TradeListDto {
    private Integer id;
    private String account;
    private String type;
    private Double buyQuantity;
}
