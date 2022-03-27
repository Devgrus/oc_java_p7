package com.nnk.springboot.dto;

import lombok.*;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
@Builder
public class BidAddDto {
    @NotBlank(message = "Account is mandatory")
    @Size(max = 30, message = "Maximum 30 characters")
    private String account;

    @NotBlank(message = "Type is mandatory")
    @Size(max = 30, message = "Maximum 30 characters")
    private String type;

    @NotNull(message = "Bid Quantity is mandatory")
    @Positive(message = "Bid Quantity must be positive")
    @Digits(integer = 10 , fraction = 2)
    private BigDecimal bidQuantity;
}
