package com.nnk.springboot.domain;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Rating")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 125)
    private String moodysRating;

    @Size(max = 125)
    private String sandPRating;

    @Size(max = 125)
    private String fitchRating;

    private Integer orderNumber;
}
