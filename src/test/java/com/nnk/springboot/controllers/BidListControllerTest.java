package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidAddDto;
import com.nnk.springboot.services.BidListService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(BidListController.class)
public class BidListControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BidListService bidListService;

    @Test
    public void validateTest() throws Exception {
        //given
        BidAddDto bidAddDto = BidAddDto.builder()
                .account("AccountTest")
                .type("TypeTest")
                .bidQuantity(BigDecimal.valueOf(5))
                .build();

        BidList bidList = BidList.builder()
                .account(bidAddDto.getAccount())
                .type(bidAddDto.getType())
                .bidQuantity(bidAddDto.getBidQuantity().doubleValue())
                .build();

        //when
        when(bidListService.save(any())).thenReturn(bidList);

        //then
        mockMvc.perform(post("/bidList/validate")
                .param("account", bidAddDto.getAccount())
                .param("type", bidAddDto.getType())
                .param("bidQuantity", "5"))
                .andExpect(redirectedUrl("/bidList/list"));
    }

    @Test
    public void validateTestAccountCharactersGreaterThan30() throws Exception {
        //given

        //when

        //then
        mockMvc.perform(post("/bidList/validate")
                        .param("account", "0123456789012345678901234567890")
                        .param("type", "TypeTest")
                        .param("bidQuantity", "5"))
                .andExpect(model().attributeErrorCount("addBidDto", 1));
    }

    @Test
    public void validateTestTypeCharactersGreaterThan30() throws Exception {
        //given

        //when

        //then
        mockMvc.perform(post("/bidList/validate")
                        .param("account", "AccountTest")
                        .param("type", "0123456789012345678901234567890")
                        .param("bidQuantity", "5"))
                .andExpect(model().attributeErrorCount("addBidDto", 1));
    }

    @Test
    public void validateTestBidQuantityIsNotNumber() throws Exception {
        //given

        //when

        //then
        mockMvc.perform(post("/bidList/validate")
                        .param("account", "AccountTest")
                        .param("type", "TypeTest")
                        .param("bidQuantity", "aa"))
                .andExpect(model().attributeErrorCount("addBidDto", 1));
    }
}
