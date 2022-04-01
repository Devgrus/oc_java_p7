package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.bid.BidAddDto;
import com.nnk.springboot.dto.bid.BidUpdateDto;
import com.nnk.springboot.services.BidListService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

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
                .bidQuantity(5d)
                .build();

        BidList bidList = BidList.builder()
                .account(bidAddDto.getAccount())
                .type(bidAddDto.getType())
                .bidQuantity(bidAddDto.getBidQuantity())
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
                .andExpect(model().attributeErrorCount("bidAddDto", 1));
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
                .andExpect(model().attributeErrorCount("bidAddDto", 1));
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
                .andExpect(model().attributeErrorCount("bidAddDto", 1));
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
                .bidQuantity(bidList.getBidQuantity())
                .build();

        Integer id = 1;
        //when
        when(bidListService.getBidUpdateFormData(id)).thenReturn(bidUpdateDto);

        //then
        mockMvc.perform(get("/bidList/update/" + id))
                .andExpect(model().attribute("bidUpdateDto", bidUpdateDto));
    }

    @Test
    public void showUpdateFormTestBidListIdNotFound() throws Exception {
        //given

        Integer id = 1;
        //when
        when(bidListService.getBidUpdateFormData(id)).thenThrow(new NoSuchElementException("ID NOT FOUND"));

        //then
        mockMvc.perform(get("/bidList/update/" + id))
                .andExpect(redirectedUrl("/bidList/list"));
    }

    @Test
    public void updateBidTest() throws Exception {
        //given
        int id = 1;

        BidUpdateDto bidUpdateDto = BidUpdateDto.builder()
                .account("AccountTest")
                .type("AccountTest")
                .bidQuantity(5d)
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
                .andExpect(model().attributeErrorCount("bidUpdateDto", 1));
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
                .andExpect(model().attributeErrorCount("bidUpdateDto", 1));
    }

    @Test
    public void deleteBidTest() throws Exception {
        //given
        Integer id = 1;

        //when
        doNothing().when(bidListService).delete(id);
        //then
        mockMvc.perform(get("/bidList/delete/" + id))
                .andExpect(redirectedUrl("/bidList/list"));
    }
}
