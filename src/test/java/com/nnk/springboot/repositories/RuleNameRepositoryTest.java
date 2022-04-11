package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.RuleName;
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
public class RuleNameRepositoryTest {

    @Autowired
    private RuleNameRepository ruleNameRepository;

    @BeforeEach
    public void initEach() {
        ruleNameRepository.deleteAll();
        ruleNameRepository.save(RuleName.builder()
                .name("name")
                .description("description")
                .json("json")
                .template("template")
                .sqlStr("sqlStr")
                .sqlPart("sqlPart")
                .build());
    }

    @Test
    public void readTest() {
        assertThat(ruleNameRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    public void createTest() {
        RuleName ruleName = RuleName.builder()
                .name("name2")
                .description("description2")
                .json("json2")
                .template("template2")
                .sqlStr("sqlStr2")
                .sqlPart("sqlPart2")
                .build();

        assertThat(ruleNameRepository.save(ruleName).getName()).isEqualTo(ruleName.getName());
    }

    @Test
    public void updateTest() {
        assertThat(ruleNameRepository.findById(1).isPresent()).isTrue();

        String newName = "name3";

        ruleNameRepository.findById(1).ifPresent(i->i.setName(newName));

        assertThat(ruleNameRepository.findById(1).get().getName()).isEqualTo(newName);

    }

    @Test
    public void deleteByIdTest() {
        assertThat(ruleNameRepository.findById(1).isPresent()).isTrue();

        ruleNameRepository.deleteById(1);

        assertThat(ruleNameRepository.findById(1).isPresent()).isFalse();
    }
}
