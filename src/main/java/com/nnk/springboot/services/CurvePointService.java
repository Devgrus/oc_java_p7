package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.curvePoint.CurvePointListDto;
import com.nnk.springboot.dto.curvePoint.CurvePointUpdateDto;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CurvePointService {

    private final CurvePointRepository curvePointRepository;

    @Autowired
    public CurvePointService(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }

    /**
     * Save a curve point
     * @param curvePoint curve point information
     * @return curve point
     */
    @Transactional
    public CurvePoint save(CurvePoint curvePoint) {
        return curvePointRepository.save(curvePoint);
    }

    /**
     * Get all curve point list
     * @return all curve point list
     */
    public List<CurvePointListDto> getAllCurvePointList() {
        List<CurvePointListDto> dto = new ArrayList<>();
        curvePointRepository.findAll().forEach(curvePoint ->
                dto.add(CurvePointListDto.builder()
                        .id(curvePoint.getId())
                        .curveId(curvePoint.getCurveId())
                        .term(curvePoint.getTerm())
                        .value(curvePoint.getValue())
                        .build()));
        return dto;
    }

    /**
     * Get a curve point information before update
     * @param id curve point id
     * @return curve point information
     */
    @Transactional
    public CurvePointUpdateDto getCurvePointUpdateFormData(Integer id) {
        CurvePoint curvePoint = curvePointRepository.findById(id).orElseThrow(()-> new NoSuchElementException("ID NOT FOUND"));
        return CurvePointUpdateDto.builder()
                .curveId(curvePoint.getCurveId())
                .term(curvePoint.getTerm())
                .value(curvePoint.getValue())
                .build();
    }

    /**
     * Update a curve point
     * @param id curve point id
     * @param dto curve point information
     */
    @Transactional
    public void update(Integer id, CurvePointUpdateDto dto) {
        CurvePoint curvePoint = curvePointRepository.findById(id).orElseThrow(()-> new NoSuchElementException("ID NOT FOUND"));
        curvePoint.setCurveId(dto.getCurveId());
        curvePoint.setTerm(dto.getTerm());
        curvePoint.setValue(dto.getValue());
    }

    /**
     * Delete curve point
     * @param id curve point id
     * @throws Exception id not found in DB
     */
    public void delete(Integer id) throws RuntimeException {
        curvePointRepository.deleteById(id);
    }
}
