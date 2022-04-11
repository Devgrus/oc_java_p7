package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Trade;
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
public class TradeRepositoryTest {

    @Autowired
    private TradeRepository tradeRepository;

    @BeforeEach
    public void initEach() {
        tradeRepository.deleteAll();
        tradeRepository.save(Trade.builder()
                .account("account")
                .type("type")
                .buyQuantity(5d)
                .build());
    }

    @Test
    public void readTest() {
        assertThat(tradeRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    public void createTest() {
        Trade trade = Trade.builder()
                .account("account2")
                .type("type2")
                .buyQuantity(10d)
                .build();

        assertThat(tradeRepository.save(trade).getAccount()).isEqualTo(trade.getAccount());
    }

    @Test
    public void updateTest() {
        assertThat(tradeRepository.findById(1).isPresent()).isTrue();

        String newAccount = "account3";

        tradeRepository.findById(1).ifPresent(i->i.setAccount(newAccount));

        assertThat(tradeRepository.findById(1).get().getAccount()).isEqualTo(newAccount);
    }

    @Test
    public void deleteByIdTest() {
        assertThat(tradeRepository.findById(1).isPresent()).isTrue();

        tradeRepository.deleteById(1);

        assertThat(tradeRepository.findById(1).isPresent()).isFalse();
    }

}
