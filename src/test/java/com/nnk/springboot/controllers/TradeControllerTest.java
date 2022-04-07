package com.nnk.springboot.controllers;

import com.nnk.springboot.config.WithMockCustomUser;
import com.nnk.springboot.config.auth.CustomUserDetailsService;
import com.nnk.springboot.config.oauth.CustomOauth2UserService;
import com.nnk.springboot.domain.Role;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.trade.TradeAddDto;
import com.nnk.springboot.dto.trade.TradeUpdateDto;
import com.nnk.springboot.services.TradeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(TradeController.class)
public class TradeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TradeService tradeService;

    @MockBean
    CustomUserDetailsService customUserDetailsService;

    @MockBean
    CustomOauth2UserService customOauth2UserService;

    @Test
    @WithMockCustomUser(role = Role.ADMIN)
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
    @WithMockCustomUser(role = Role.ADMIN)
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

    @Test
    @WithMockCustomUser(role = Role.ADMIN)
    public void showUpdateFormTest() throws Exception {
        //given
        Integer id = 1;

        TradeUpdateDto dto = TradeUpdateDto.builder().account("AA").type("aa").buyQuantity(5d).build();

        //when
        when(tradeService.getTradeUpdateFormData(id)).thenReturn(dto);

        //then
        mockMvc.perform(get("/trade/update/" + id))
                .andExpect(model().attribute("trade", dto));
    }

    @Test
    @WithMockCustomUser(role = Role.ADMIN)
    public void showUpdateFormTestIdNotFound() throws Exception {
        //given
        Integer id = 1;

        //when
        when(tradeService.getTradeUpdateFormData(id)).thenThrow(new NoSuchElementException("ID NOT FOUND"));

        //then
        mockMvc.perform(get("/trade/update/" + id))
                .andExpect(redirectedUrl("/trade/list"));
    }

    @Test
    @WithMockCustomUser(role = Role.ADMIN)
    public void updateTradeTest() throws Exception {
        //given
        int id = 1;

        TradeUpdateDto dto = TradeUpdateDto.builder().account("AA").type("aa").buyQuantity(5d).build();

        //when
        doNothing().when(tradeService).update(any(), any());

        //then
        mockMvc.perform(post("/trade/update/" + id)
                .param("account", dto.getAccount())
                .param("type", dto.getType())
                .param("buyQuantity", dto.getBuyQuantity().toString()))
                .andExpect(redirectedUrl("/trade/list"));
    }

    @Test
    @WithMockCustomUser(role = Role.ADMIN)
    public void updateTradeTestIdNotFound() throws Exception {
        //given
        int id = 1;

        TradeUpdateDto dto = TradeUpdateDto.builder().account("AA").type("aa").buyQuantity(5d).build();

        //when
        doThrow(NoSuchElementException.class).when(tradeService).update(id, dto);

        //then
        mockMvc.perform(post("/trade/update/" + id)
                        .param("account", dto.getAccount())
                        .param("type", dto.getType())
                        .param("buyQuantity", dto.getBuyQuantity().toString()))
                .andExpect(redirectedUrl("/trade/list"));
    }

    @Test
    @WithMockCustomUser(role = Role.ADMIN)
    public void deleteTradeTest() throws Exception {
        //given
        Integer id = 1;

        //when
        doNothing().when(tradeService).delete(id);

        //then
        mockMvc.perform(get("/trade/delete/" + id))
                .andExpect(redirectedUrl("/trade/list"));
    }
}
