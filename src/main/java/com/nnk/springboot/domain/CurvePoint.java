package com.nnk.springboot.domain;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "CurvePoint")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurvePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @NotNull(message = "must not be null")
    private Integer CurveId;
    private Timestamp asOfDate;
    private Double term;
    private Double value;
    private Timestamp creationDate;
}
