package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.bid.BidUpdateDto;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class BidListServiceTest {

    @Mock
    BidListRepository bidListRepository;

    @InjectMocks
    BidListService bidListService;

    @Test
    public void findByListIdTest() {
        //given
        Optional<BidList> bidList = Optional.of(new BidList());
        Integer id = 1;

        //when
        when(bidListRepository.findById(id)).thenReturn(bidList);

        //then
        assertThat(bidListService.findById(id)).isEqualTo(bidList.get());
    }

    @Test
    public void findByListIdTestBidListNotFound() {
        //given
        Integer id = 1;

        //when
        when(bidListRepository.findById(id)).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(()->bidListService.findById(id)).hasMessage("ID NOT FOUND");
    }

    @Test
    public void getBidUpdateFormDataTest() {
        //given
        BidList bidList = BidList.builder()
                .id(1)
                .account("A")
                .type("T")
                .bidQuantity(2d)
                .build();
        //when
        when(bidListRepository.findById(bidList.getId())).thenReturn(Optional.of(bidList));

        //then
        assertThat(bidListService.getBidUpdateFormData(bidList.getId()).getAccount()).isEqualTo(bidList.getAccount());
        assertThat(bidListService.getBidUpdateFormData(bidList.getId()).getType()).isEqualTo(bidList.getType());
        assertThat(bidListService.getBidUpdateFormData(bidList.getId()).getBidQuantity()).isEqualTo(bidList.getBidQuantity());
    }

    @Test
    public void getBidUpdateFormDataTestIdNotFound() {
        //given
        Integer id = 1;
        //when
        when(bidListRepository.findById(id)).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(()->bidListService.getBidUpdateFormData(id)).hasMessage("ID NOT FOUND");
    }

    @Test
    public void updateTest() {
        //given
        BidList bidList = BidList.builder()
                .id(1)
                .account("A")
                .type("T")
                .bidQuantity(2d)
                .build();

        BidUpdateDto dto = BidUpdateDto.builder()
                .account("Account")
                .type("Type")
                .bidQuantity(5d)
                .build();
        //when
        when(bidListRepository.findById(bidList.getId())).thenReturn(Optional.of(bidList));
        bidListService.update(bidList.getId(), dto);

        //then
        assertThat(bidList.getAccount()).isEqualTo(dto.getAccount());
        assertThat(bidList.getType()).isEqualTo(dto.getType());
        assertThat(bidList.getBidQuantity()).isEqualTo(dto.getBidQuantity().doubleValue());
    }

    @Test
    public void updateTestIdNotFound() {
        //given
        Integer id = 1;

        BidUpdateDto dto = BidUpdateDto.builder()
                .account("Account")
                .type("Type")
                .bidQuantity(5d)
                .build();
        //when
        when(bidListRepository.findById(id)).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(()->bidListService.update(id, dto)).hasMessage("ID NOT FOUND");
    }
}
