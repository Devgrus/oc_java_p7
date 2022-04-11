package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.CurvePoint;
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
public class CurvePointRepositoryTest {

    @Autowired
    private CurvePointRepository curvePointRepository;


    @BeforeEach
    public void initEach() {
        curvePointRepository.deleteAll();
        curvePointRepository.save(CurvePoint.builder()
                .term(5d)
                .curveId(5)
                .value(5d)
                .build());
    }

    @Test
    public void readTest() {
        assertThat(curvePointRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    public void createTest() {
        CurvePoint curvePoint = CurvePoint.builder()
                .term(10d)
                .curveId(5)
                .value(5d)
                .build();

        assertThat(curvePointRepository.save(curvePoint).getTerm()).isEqualTo(curvePoint.getTerm());
    }

    @Test
    public void updateTest() {
        assertThat(curvePointRepository.findById(1).isPresent()).isTrue();

        Double newTerm = 10d;

        curvePointRepository.findById(1).ifPresent(i->i.setTerm(newTerm));

        assertThat(curvePointRepository.findById(1).get().getTerm()).isEqualTo(newTerm);
    }

    @Test
    public void deleteByIdTest() {
        assertThat(curvePointRepository.findById(1).isPresent()).isTrue();

        curvePointRepository.deleteById(1);

        assertThat(curvePointRepository.findById(1).isPresent()).isFalse();
    }
}
