package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.trade.TradeUpdateDto;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TradeServiceTest {

    @Mock
    TradeRepository tradeRepository;

    @InjectMocks
    TradeService tradeService;

    @Test
    public void getTradeUpdateFormDataTest() throws Exception {
        //given
        Trade trade = Trade.builder()
                .id(1).account("AA").type("aa").buyQuantity(5d).build();

        //when
        when(tradeRepository.findById(trade.getId())).thenReturn(Optional.of(trade));

        //then
        assertThat(tradeService.getTradeUpdateFormData(trade.getId()).getAccount()).isEqualTo(trade.getAccount());
        assertThat(tradeService.getTradeUpdateFormData(trade.getId()).getType()).isEqualTo(trade.getType());
        assertThat(tradeService.getTradeUpdateFormData(trade.getId()).getBuyQuantity()).isEqualTo(trade.getBuyQuantity());
    }

    @Test
    public void getTradeUpdateFormDataTestIdNotFound() throws Exception {
        //given
        Integer id = 1;

        //when
        when(tradeRepository.findById(id)).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(()-> tradeService.getTradeUpdateFormData(id)).hasMessage("ID NOT FOUND");
    }

    @Test
    public void updateTest() throws Exception {
        //given
        Trade trade = Trade.builder()
                .id(1).account("AA").type("aa").buyQuantity(5d).build();

        TradeUpdateDto dto = TradeUpdateDto.builder()
                .account("BB").type("bb").buyQuantity(6d).build();

        //when
        when(tradeRepository.findById(trade.getId())).thenReturn(Optional.of(trade));
        tradeService.update(trade.getId(), dto);

        //then
        assertThat(trade.getAccount()).isEqualTo(dto.getAccount());
        assertThat(trade.getType()).isEqualTo(dto.getType());
        assertThat(trade.getBuyQuantity()).isEqualTo(dto.getBuyQuantity());
    }

    @Test
    public void updateTestIdNotFound() throws Exception {
        //given
        Integer id = 1;

        TradeUpdateDto dto = TradeUpdateDto.builder()
                .account("BB").type("bb").buyQuantity(6d).build();

        //when
        when(tradeRepository.findById(id)).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(()-> tradeService.update(id, dto)).hasMessage("ID NOT FOUND");
    }
}
