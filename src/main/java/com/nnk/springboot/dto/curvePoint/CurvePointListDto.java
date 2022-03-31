package com.nnk.springboot.dto.curvePoint;

import lombok.*;

@Data
@Builder
public class CurvePointListDto {
    private Integer id;
    private Integer curveId;
    private Double term;
    private Double value;
}
