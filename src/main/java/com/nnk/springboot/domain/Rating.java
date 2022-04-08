package com.nnk.springboot.domain;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;

@Entity
@Table(name = "rating")
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
    @Column(name = "sand_p_rating")
    private String sandPRating;

    @Size(max = 125)
    private String fitchRating;

    private Integer orderNumber;
}
