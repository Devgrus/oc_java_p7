package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.bid.BidListDto;
import com.nnk.springboot.dto.bid.BidUpdateDto;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BidListService {

    private final BidListRepository bidListRepository;

    @Autowired
    public BidListService(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }

    /**
     * Save a bid
     * @param bid bid information
     * @return bidList
     */
    @Transactional
    public BidList save(BidList bid) {
        return bidListRepository.save(bid);
    }

    /**
     * Find a bid info by id
     * @param id
     * @return bidList
     */
    @Transactional
    public BidList findByBidListId(Integer id) {
        return bidListRepository.findByBidListId(id).orElseThrow(()-> new NoSuchElementException("ID NOT FOUND"));
    }

    /**
     * Get all bid list
     * @return all bid list
     */
    public List<BidListDto> getAllBidList() {
        List<BidListDto> dto = new ArrayList<>();
        bidListRepository.findAll().forEach(bidList ->
                dto.add(BidListDto.builder()
                        .id(bidList.getBidListId())
                        .account(bidList.getAccount())
                        .type(bidList.getType())
                        .bidQuantity(BigDecimal.valueOf(bidList.getBidQuantity()))
                        .build()));
        return dto;
    }

    /**
     * Get a bid information before update
     * @param id bidListId
     * @return bid information
     */
    @Transactional
    public  BidUpdateDto getBidUpdateFormData(Integer id) {
        BidList bidList = bidListRepository.findByBidListId(id).orElseThrow(()-> new NoSuchElementException("ID NOT FOUND"));
        return BidUpdateDto.builder()
                .account(bidList.getAccount())
                .type(bidList.getType())
                .bidQuantity(BigDecimal.valueOf(bidList.getBidQuantity()))
                .build();
    }

    /**
     * Update BidList
     * @param id bidListId
     * @param dto Bid update information
     */
    @Transactional
    public void update(Integer id, BidUpdateDto dto) {
        BidList bidList = bidListRepository.findByBidListId(id).orElseThrow(()-> new NoSuchElementException("ID NOT FOUND"));
        bidList.setAccount(dto.getAccount());
        bidList.setType(dto.getType());
        bidList.setBidQuantity(dto.getBidQuantity().doubleValue());
    }

    /**
     * Delete bidList
     * @param id bidListId
     * @throws EmptyResultDataAccessException Id not found in DB
     */
    public void delete(Integer id) throws EmptyResultDataAccessException {
        bidListRepository.deleteById(id);
    }
}
