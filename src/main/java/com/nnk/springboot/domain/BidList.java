package com.nnk.springboot.domain;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;

@Entity
@Table(name = "BidList")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BidList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bidListId;

    @NotBlank(message = "Account is mandatory")
    @Size(max = 30)
    private String account;

    @NotBlank(message = "Type is mandatory")
    @Size(max = 30)
    private String type;

    private Double bidQuantity;
    private Double askQuantity;
    private Double bid;
    private Double ask;

    @Size(max = 125)
    private String benchmark;

    private Timestamp bidListDate;

    @Size(max = 125)
    private String commentary;

    @Size(max = 125)
    private String security;

    @Size(max = 10)
    private String status;

    @Size(max = 125)
    private String trader;

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
