package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.BidList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class BidListRepositoryTest {

    @Autowired
    private BidListRepository bidListRepository;

    @BeforeEach
    public void initEach() {
        bidListRepository.deleteAll();
        bidListRepository.save(BidList.builder()
                .account("account")
                .type("type")
                .bidQuantity(10d)
                .build());
    }

    @Test
    public void readTest() {
        assertThat(bidListRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    public void createTest() {

        BidList bidList2 = BidList.builder()
                .account("accountt")
                .type("typee")
                .bidQuantity(10d)
                .build();

        assertThat(bidListRepository.save(bidList2).getType()).isEqualTo(bidList2.getType());
    }

    @Test
    public void updateTest() {
        assertThat(bidListRepository.findById(1).isPresent()).isTrue();

        String newType = "typeee";

        bidListRepository.findById(1).ifPresent(i->i.setType(newType));

        assertThat(bidListRepository.findById(1).get().getType()).isEqualTo(newType);
    }

    @Test
    public void deleteByIdTest() {
        assertThat(bidListRepository.findById(1).isPresent()).isTrue();

        bidListRepository.deleteById(1);

        assertThat(bidListRepository.findById(1).isPresent()).isFalse();
    }
}
