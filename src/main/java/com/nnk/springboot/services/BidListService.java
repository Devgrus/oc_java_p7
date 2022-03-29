package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidUpdateDto;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.NoSuchElementException;

@Service
public class BidListService {

    private final BidListRepository bidListRepository;

    @Autowired
    public BidListService(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }

    @Transactional
    public BidList save(BidList bid) {
        return bidListRepository.save(bid);
    }

    @Transactional
    public BidList findByBidListId(Integer id) {
        return bidListRepository.findByBidListId(id).orElseThrow(()-> new NoSuchElementException("ID NOT FOUND"));
    }

    public  BidUpdateDto getBidUpdateFormData(Integer id) {
        BidList bidList = bidListRepository.findByBidListId(id).orElseThrow(()-> new NoSuchElementException("ID NOT FOUND"));
        return BidUpdateDto.builder()
                .account(bidList.getAccount())
                .type(bidList.getType())
                .bidQuantity(BigDecimal.valueOf(bidList.getBidQuantity()))
                .build();
    }

    @Transactional
    public void update(Integer id, BidUpdateDto dto) {
        BidList bidList = bidListRepository.findByBidListId(id).orElseThrow(()-> new NoSuchElementException("ID NOT FOUND"));
        bidList.setAccount(dto.getAccount());
        bidList.setType(dto.getType());
        bidList.setBidQuantity(dto.getBidQuantity().doubleValue());
    }
}
