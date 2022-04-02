package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.trade.TradeUpdateDto;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@Service
public class TradeService {

    private final TradeRepository tradeRepository;

    @Autowired
    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    /**
     * Save a trade
     * @param trade trade information
     * @return trade
     */
    @Transactional
    public Trade save(Trade trade) {
        return tradeRepository.save(trade);
    }

    /**
     * Get a trade information before update
     * @param id trade id
     * @return trade information
     */
    @Transactional
    public TradeUpdateDto getTradeUpdateFormData(Integer id) {
        Trade trade = tradeRepository.findById(id).orElseThrow(()-> new NoSuchElementException("ID NOT FOUND"));
        return TradeUpdateDto.builder()
                .account(trade.getAccount())
                .type(trade.getType())
                .buyQuantity(trade.getBuyQuantity())
                .build();
    }

    /**
     * Update a trade
     * @param id trade id
     * @param dto trade information
     */
    @Transactional
    public void update(Integer id, TradeUpdateDto dto) {
        Trade trade = tradeRepository.findById(id).orElseThrow(()-> new NoSuchElementException("ID NOT FOUND"));
        if(!trade.getAccount().equals(dto.getAccount())) trade.setAccount(dto.getAccount());
        if(!trade.getType().equals(dto.getType())) trade.setType(dto.getType());
        if(!trade.getBuyQuantity().equals(dto.getBuyQuantity())) trade.setBuyQuantity(dto.getBuyQuantity());
    }
}
