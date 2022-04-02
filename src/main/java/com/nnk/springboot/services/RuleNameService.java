package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.ruleName.RuleNameUpdateDto;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@Service
public class RuleNameService {

    private final RuleNameRepository ruleNameRepository;

    @Autowired
    public RuleNameService(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }

    /**
     * Save a rule name
     * @param ruleName rule name information
     * @return rule name
     */
    @Transactional
    public RuleName save(RuleName ruleName) {
        return ruleNameRepository.save(ruleName);
    }

    /**
     * Get a rule name information before update
     * @param id rule name id
     * @return rule name information
     */
    public RuleNameUpdateDto getRuleNameUpdateFormData(Integer id) {
        RuleName ruleName = ruleNameRepository.findById(id).orElseThrow(()-> new NoSuchElementException("ID NOT FOUND"));
        return RuleNameUpdateDto.builder()
                .name(ruleName.getName())
                .description(ruleName.getDescription())
                .build();
    }

    /**
     * Update a rule name
     * @param id rule name id
     * @param dto rule name information
     */
    @Transactional
    public void update(Integer id, RuleNameUpdateDto dto) {
        RuleName ruleName = ruleNameRepository.findById(id).orElseThrow(()-> new NoSuchElementException("ID NOT FOUND"));
        if(!ruleName.getName().equals(dto.getName())) ruleName.setName(dto.getName());
        if(!ruleName.getDescription().equals(dto.getDescription())) ruleName.setDescription(dto.getDescription());
        if(!ruleName.getJson().equals(dto.getJson())) ruleName.setJson(dto.getJson());
        if(!ruleName.getSqlStr().equals(dto.getSqlStr())) ruleName.setSqlStr(dto.getSqlStr());
        if(!ruleName.getSqlPart().equals(dto.getSqlPart())) ruleName.setSqlPart(dto.getSqlPart());
    }
}
