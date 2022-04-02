package com.nnk.springboot.dto.trade;

import com.nnk.springboot.domain.Trade;
import lombok.*;

import javax.validation.constraints.*;

@Data
@Builder
public class TradeAddDto {
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

    public Trade toEntity() {
        return Trade.builder()
                .account(account)
                .type(type)
                .buyQuantity(buyQuantity)
                .build();
    }
}
