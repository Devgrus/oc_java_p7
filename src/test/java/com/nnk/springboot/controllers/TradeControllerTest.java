package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.trade.TradeAddDto;
import com.nnk.springboot.services.TradeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(TradeController.class)
public class TradeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TradeService tradeService;

    @Test
    public void validateTest() throws Exception {
        //given
        TradeAddDto dto = TradeAddDto.builder()
                .account("AA").type("TT").buyQuantity(5d).build();

        Trade trade = dto.toEntity();

        //when
        when(tradeService.save(any())).thenReturn(trade);

        //then
        mockMvc.perform(post("/trade/validate")
                .param("account", dto.getAccount())
                .param("type", dto.getType())
                .param("buyQuantity", dto.getBuyQuantity().toString()))
                .andExpect(redirectedUrl("/trade/list"));
    }

    @Test
    public void validateTestAccountAndTypeAreBlank() throws Exception {
        //given
        TradeAddDto dto = TradeAddDto.builder()
                .account(" ").type(" ").buyQuantity(5d).build();

        //when

        //then
        mockMvc.perform(post("/trade/validate")
                        .param("account", dto.getAccount())
                        .param("type", dto.getType())
                        .param("buyQuantity", dto.getBuyQuantity().toString()))
                .andExpect(model().attributeErrorCount("trade", 2));
    }
}
