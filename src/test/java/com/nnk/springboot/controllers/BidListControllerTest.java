package com.nnk.springboot.controllers;

import com.nnk.springboot.config.WithMockCustomUser;
import com.nnk.springboot.config.auth.CustomUserDetailsService;
import com.nnk.springboot.config.oauth.CustomOauth2UserService;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Role;
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

    @MockBean
    CustomUserDetailsService customUserDetailsService;

    @MockBean
    CustomOauth2UserService customOauth2UserService;

    @Test
    @WithMockCustomUser(role = Role.ADMIN)
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
    @WithMockCustomUser(role = Role.ADMIN)
    public void validateTestAccountCharactersGreaterThan30() throws Exception {
        //given

        //when

        //then
        mockMvc.perform(post("/bidList/validate")
                        .param("account", "0123456789012345678901234567890")
                        .param("type", "TypeTest")
                        .param("bidQuantity", "5"))
                .andExpect(model().attributeErrorCount("bid", 1));
    }

    @Test
    @WithMockCustomUser(role = Role.ADMIN)
    public void validateTestTypeCharactersGreaterThan30() throws Exception {
        //given

        //when

        //then
        mockMvc.perform(post("/bidList/validate")
                        .param("account", "AccountTest")
                        .param("type", "0123456789012345678901234567890")
                        .param("bidQuantity", "5"))
                .andExpect(model().attributeErrorCount("bid", 1));
    }

    @Test
    @WithMockCustomUser(role = Role.ADMIN)
    public void validateTestBidQuantityIsNotNumber() throws Exception {
        //given

        //when

        //then
        mockMvc.perform(post("/bidList/validate")
                        .param("account", "AccountTest")
                        .param("type", "TypeTest")
                        .param("bidQuantity", "aa"))
                .andExpect(model().attributeErrorCount("bid", 1));
    }

    @Test
    @WithMockCustomUser(role = Role.ADMIN)
    public void showUpdateFormTest() throws Exception {
        //given
        BidList bidList = BidList.builder()
                .id(1)
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
                .andExpect(model().attribute("bid", bidUpdateDto));
    }

    @Test
    @WithMockCustomUser(role = Role.ADMIN)
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
    @WithMockCustomUser(role = Role.ADMIN)
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
    @WithMockCustomUser(role = Role.ADMIN)
    public void updateBidTestAccountCharactersGreaterThan30() throws Exception {
        //given

        //when

        //then
        mockMvc.perform(post("/bidList/update/1")
                        .param("account", "0123456789012345678901234567890")
                        .param("type", "TypeTest")
                        .param("bidQuantity", "5"))
                .andExpect(model().attributeErrorCount("bid", 1));
    }

    @Test
    @WithMockCustomUser(role = Role.ADMIN)
    public void updateBidTestTypeCharactersGreaterThan30() throws Exception {
        //given

        //when

        //then
        mockMvc.perform(post("/bidList/update/1")
                        .param("account", "AccountTest")
                        .param("type", "0123456789012345678901234567890")
                        .param("bidQuantity", "5"))
                .andExpect(model().attributeErrorCount("bid", 1));
    }

    @Test
    @WithMockCustomUser(role = Role.ADMIN)
    public void updateBidTestBidQuantityIsNotNumber() throws Exception {
        //given

        //when

        //then
        mockMvc.perform(post("/bidList/update/1")
                        .param("account", "AccountTest")
                        .param("type", "TypeTest")
                        .param("bidQuantity", "aa"))
                .andExpect(model().attributeErrorCount("bid", 1));
    }

    @Test
    @WithMockCustomUser(role = Role.ADMIN)
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
