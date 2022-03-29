package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidAddDto;
import com.nnk.springboot.dto.BidUpdateDto;
import com.nnk.springboot.services.BidListService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

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

    @Test
    public void showUpdateFormTest() throws Exception {
        //given
        BidList bidList = BidList.builder()
                .bidListId(1)
                .account("AccountTest")
                .type("TypeTest")
                .bidQuantity(5d)
                .build();

        BidUpdateDto bidUpdateDto = BidUpdateDto.builder()
                .account(bidList.getAccount())
                .type(bidList.getType())
                .bidQuantity(BigDecimal.valueOf(bidList.getBidQuantity()))
                .build();

        Integer id = 1;
        //when
        when(bidListService.getBidUpdateFormData(id)).thenReturn(bidUpdateDto);

        //then
        mockMvc.perform(get("/bidList/update/" + id.toString()))
                .andExpect(model().attribute("bidUpdateDto", bidUpdateDto));
    }

    @Test
    public void showUpdateFormTestBidListIdNotFound() throws Exception {
        //given

        Integer id = 1;
        //when
        when(bidListService.getBidUpdateFormData(id)).thenThrow(new NoSuchElementException("ID NOT FOUND"));

        //then
        mockMvc.perform(get("/bidList/update/" + id.toString()))
                .andExpect(redirectedUrl("/bidList/list"));
    }

    @Test
    public void updateBidTest() throws Exception {
        //given
        Integer id = 1;

        BidUpdateDto bidUpdateDto = BidUpdateDto.builder()
                .account("AccountTest")
                .type("AccountTest")
                .bidQuantity(BigDecimal.valueOf(5d))
                .build();

        //when
        doNothing().when(bidListService).update(any(), any());

        //then
        mockMvc.perform(post("/bidList/update/" + id)
                        .param("account", bidUpdateDto.getAccount())
                        .param("type", bidUpdateDto.getType())
                        .param("bidQuantity", bidUpdateDto.getBidQuantity().toString()))
                .andExpect(redirectedUrl("/bidList/list"));
    }

    @Test
    public void updateBidTestAccountCharactersGreaterThan30() throws Exception {
        //given

        //when

        //then
        mockMvc.perform(post("/bidList/update/1")
                        .param("account", "0123456789012345678901234567890")
                        .param("type", "TypeTest")
                        .param("bidQuantity", "5"))
                .andExpect(model().attributeErrorCount("bidUpdateDto", 1));
    }

    @Test
    public void updateBidTestTypeCharactersGreaterThan30() throws Exception {
        //given

        //when

        //then
        mockMvc.perform(post("/bidList/update/1")
                        .param("account", "AccountTest")
                        .param("type", "0123456789012345678901234567890")
                        .param("bidQuantity", "5"))
                .andExpect(model().attributeErrorCount("addBidDto", 1));
    }

    @Test
    public void updateBidTestBidQuantityIsNotNumber() throws Exception {
        //given

        //when

        //then
        mockMvc.perform(post("/bidList/update/1")
                        .param("account", "AccountTest")
                        .param("type", "TypeTest")
                        .param("bidQuantity", "aa"))
                .andExpect(model().attributeErrorCount("addBidDto", 1));
    }
}
