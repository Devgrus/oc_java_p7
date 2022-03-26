package com.nnk.springboot.domain;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Trade")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer TradeId;

    @NotBlank(message = "Account is mandatory")
    @Size(max = 30)
    private String account;

    @NotBlank(message = "Type is mandatory")
    @Size(max = 30)
    private String type;

    private Double buyQuantity;
    private Double sellQuantity;
    private Double buyPrice;
    private Double sellPrice;

    private Timestamp tradeDate;

    @Size(max = 125)
    private String security;

    @Size(max = 10)
    private String status;

    @Size(max = 125)
    private String trader;

    @Size(max = 125)
    private String benchmark;

    @Size(max = 125)
    private String book;

    @Size(max = 125)
    private String creationName;

    private Timestamp creationDate;

    @Size(max = 125)
    private String revisionName;

    private Timestamp revisionDate;

    @Size(max = 125)
    private String dealName;

    @Size(max = 125)
    private String dealType;

    @Size(max = 125)
    private String sourceListId;

    @Size(max = 125)
    private String side;
}
