package com.nnk.springboot.dto.bid;

import lombok.*;

@Data
@Builder
public class BidListDto {
    private Integer id;
    private String account;
    private String type;
    private Double bidQuantity;
}
