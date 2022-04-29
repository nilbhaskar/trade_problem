package com.assessment.tradeFlow.dao;

import com.assessment.tradeFlow.model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepo extends JpaRepository<Trade,String> {

}
