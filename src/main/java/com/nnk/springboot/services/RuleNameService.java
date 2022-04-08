package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.ruleName.RuleNameListDto;
import com.nnk.springboot.dto.ruleName.RuleNameUpdateDto;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
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
     * Get all rule name list
     * @return all rule name list
     */
    public List<RuleNameListDto> getAllRuleNameList() {
        List<RuleNameListDto> dto = new ArrayList<>();
        ruleNameRepository.findAll().forEach(ruleName ->
                dto.add(RuleNameListDto.builder()
                        .id(ruleName.getId())
                        .name(ruleName.getName())
                        .description(ruleName.getDescription())
                        .json(ruleName.getJson())
                        .template(ruleName.getTemplate())
                        .sqlStr(ruleName.getSqlStr())
                        .sqlPart(ruleName.getSqlPart())
                        .build()));
        return dto;
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
                .json(ruleName.getJson())
                .template(ruleName.getTemplate())
                .sqlStr(ruleName.getSqlStr())
                .sqlPart(ruleName.getSqlPart())
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

    /**
     * Delete a rule name
     * @param id rule name id
     * @throws RuntimeException id not found in DB
     */
    @Transactional
    public void delete(Integer id) throws RuntimeException{
        ruleNameRepository.deleteById(id);
    }
}
