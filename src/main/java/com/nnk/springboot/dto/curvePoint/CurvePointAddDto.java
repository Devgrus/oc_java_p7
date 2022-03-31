package com.nnk.springboot.dto.curvePoint;

import com.nnk.springboot.domain.CurvePoint;
import lombok.*;

import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.time.Instant;

@Data
@Builder
public class CurvePointAddDto {
    @NotNull(message = "Must not be null")
    @Positive(message = "Must be positive")
    private Integer curveId;

    @NotNull(message = "Must not be null")
    @Positive(message = "Must be positive")
    @Digits(integer = 10, fraction = 2, message = "${validatedValue > 9999999999.99 ? "
            + "'numeric value out of bounds (min 0.01, max 9999999999.99)':"
            + "'value cannot contain more than two fractional digits.'}")
    private Double term;

    @NotNull(message = "Must not be null")
    @Positive(message = "Must be positive")
    @Digits(integer = 10, fraction = 2, message = "${validatedValue > 9999999999.99 ? "
            + "'numeric value out of bounds (min 0.01, max 9999999999.99)':"
            + "'value cannot contain more than two fractional digits.'}")
    private Double value;

    public CurvePoint toEntity() {
        return CurvePoint.builder()
                .curveId(curveId)
                .term(term)
                .value(value)
                .creationDate(Timestamp.from(Instant.now()))
                .build();
    }
}
