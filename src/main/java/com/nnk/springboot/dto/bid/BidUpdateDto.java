package com.nnk.springboot.dto.bid;

import com.nnk.springboot.domain.BidList;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;

@Data
@Builder
public class BidUpdateDto {
    @NotBlank(message = "Account is mandatory")
    @Size(max = 30, message = "Maximum 30 characters")
    private String account;

    @NotBlank(message = "Type is mandatory")
    @Size(max = 30, message = "Maximum 30 characters")
    private String type;

    @NotNull(message = "Bid Quantity is mandatory")
    @Positive(message = "Bid Quantity must be positive")
    @Digits(integer = 10, fraction = 2, message = "${validatedValue > 9999999999.99 ? "
            + "'numeric value out of bounds (min 0.01, max 9999999999.99)':"
            + "'value cannot contain more than two fractional digits.'}")
    private Double bidQuantity;

    public BidList toEntity(Integer id) {
        return BidList.builder()
                .id(id)
                .account(account)
                .type(type)
                .bidQuantity(bidQuantity)
                .build();
    }
}
