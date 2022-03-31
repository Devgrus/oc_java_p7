package com.nnk.springboot.services;

import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CurvePointServiceTest {

    @Mock
    CurvePointRepository curvePointRepository;

    @InjectMocks
    CurvePointService curvePointService;
}
