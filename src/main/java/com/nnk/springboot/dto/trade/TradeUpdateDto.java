package com.nnk.springboot.dto.trade;

import lombok.*;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
@Builder
public class TradeUpdateDto {
    @NotBlank(message = "Account is mandatory")
    @Size(max = 30, message = "30 characters maximum")
    private String account;

    @NotBlank(message = "Type is mandatory")
    @Size(max = 30, message = "30 characters maximum")
    private String type;

    @Positive(message = "Buy Quantity must be positive")
    @Digits(integer = 10, fraction = 2, message = "${validatedValue > 9999999999.99 ? "
            + "'numeric value out of bounds (min 0.01, max 9999999999.99)':"
            + "'value cannot contain more than two fractional digits.'}")
    private Double buyQuantity;
}
