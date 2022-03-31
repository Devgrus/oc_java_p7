package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.curvePoint.CurvePointUpdateDto;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CurvePointServiceTest {

    @Mock
    CurvePointRepository curvePointRepository;

    @InjectMocks
    CurvePointService curvePointService;

    @Test
    public void getCurvePointUpdateFormDataTest() {
        //given
        CurvePoint curvePoint = CurvePoint.builder()
                .id(1)
                .curveId(2)
                .term(2d)
                .value(2d)
                .build();

        //when
        when(curvePointRepository.findById(curvePoint.getId())).thenReturn(Optional.of(curvePoint));

        //then
        assertThat(curvePointService.getCurvePointUpdateFormData(curvePoint.getId()).getCurveId()).isEqualTo(2);
        assertThat(curvePointService.getCurvePointUpdateFormData(curvePoint.getId()).getTerm()).isEqualTo(2d);
        assertThat(curvePointService.getCurvePointUpdateFormData(curvePoint.getId()).getValue()).isEqualTo(2d);
    }

    @Test
    public void getCurvePointUpdateFormDataTestIdNotFound() {
        //given
        Integer id = 1;

        //when
        when(curvePointRepository.findById(id)).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(()-> curvePointService.getCurvePointUpdateFormData(id)).hasMessage("ID NOT FOUND");
    }

    @Test
    public void updateTest() {
        //given
        CurvePoint curvePoint = CurvePoint.builder()
                .id(1)
                .curveId(2)
                .term(2d)
                .value(2d)
                .build();

        CurvePointUpdateDto dto = CurvePointUpdateDto.builder()
                .curveId(3)
                .term(3d)
                .value(3d)
                .build();

        //when
        when(curvePointRepository.findById(curvePoint.getId())).thenReturn(Optional.of(curvePoint));
        curvePointService.update(curvePoint.getId(), dto);

        //then
        assertThat(curvePoint.getCurveId()).isEqualTo(dto.getCurveId());
        assertThat(curvePoint.getTerm()).isEqualTo(dto.getTerm());
        assertThat(curvePoint.getValue()).isEqualTo(dto.getValue());
    }

    @Test
    public void updateTestIdNotFound() {
        //given
        Integer id = 1;

        CurvePointUpdateDto dto = CurvePointUpdateDto.builder()
                .curveId(3)
                .term(3d)
                .value(3d)
                .build();

        //when
        when(curvePointRepository.findById(id)).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(()-> curvePointService.update(id, dto)).hasMessage("ID NOT FOUND");
    }
}
