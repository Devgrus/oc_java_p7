package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
}
