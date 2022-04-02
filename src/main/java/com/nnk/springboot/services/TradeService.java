package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.trade.TradeListDto;
import com.nnk.springboot.dto.trade.TradeUpdateDto;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
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
     * Get all trade list
     * @return all trade list
     */
    @Transactional
    public List<TradeListDto> getAllTradeList() {
        List<TradeListDto> dto = new ArrayList<>();
        tradeRepository.findAll().forEach(trade ->
                dto.add(TradeListDto.builder()
                        .id(trade.getTradeId())
                        .account(trade.getAccount())
                        .type(trade.getType())
                        .buyQuantity(trade.getBuyQuantity())
                        .build()));
        return dto;
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
        trade.setAccount(dto.getAccount());
        trade.setType(dto.getType());
        trade.setBuyQuantity(dto.getBuyQuantity());
    }

    /**
     * Delete trade
     * @param id trade id
     * @throws RuntimeException id not found in DB
     */
    @Transactional
    public void delete(Integer id) throws RuntimeException {
        tradeRepository.deleteById(id);
    }
}
