package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Rating;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class RatingRepositoryTest {

    @Autowired
    private RatingRepository ratingRepository;

    @BeforeEach
    public void initEach() {
        ratingRepository.deleteAll();
        ratingRepository.save(Rating.builder()
                .orderNumber(1)
                .moodysRating("AA")
                .sandPRating("AA")
                .fitchRating("AAA")
                .build());
    }

    @Test
    public void readTest() {
        assertThat(ratingRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    public void createTest() {
        Rating rating = Rating.builder()
                .orderNumber(1)
                .moodysRating("BB")
                .sandPRating("BB")
                .fitchRating("BBB")
                .build();

        assertThat(ratingRepository.save(rating).getOrderNumber()).isEqualTo(rating.getOrderNumber());
    }

    @Test
    public void updateTest() {
        assertThat(ratingRepository.findById(1).isPresent()).isTrue();

        String moodysRating = "AAAAAA";

        ratingRepository.findById(1).ifPresent(i->i.setMoodysRating(moodysRating));

        assertThat(ratingRepository.findById(1).get().getMoodysRating()).isEqualTo(moodysRating);
    }

    @Test
    public void deleteByIdTest() {
        assertThat(ratingRepository.findById(1).isPresent()).isTrue();

        ratingRepository.deleteById(1);

        assertThat(ratingRepository.findById(1).isPresent()).isFalse();
    }

}
