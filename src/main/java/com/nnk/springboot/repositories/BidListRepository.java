package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.BidList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BidListRepository extends JpaRepository<BidList, Integer> {
    Optional<BidList> findByBidListId(Integer id);
    List<BidList> findAll();
}
