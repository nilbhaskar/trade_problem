package com.assessment.tradeFlow.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assessment.tradeFlow.dao.TradeDao;
import com.assessment.tradeFlow.dao.TradeRepo;
import com.assessment.tradeFlow.model.Trade;

@Service
public class TradeService {

	@Autowired
	TradeDao tradeDao;

	@Autowired
	TradeRepo tradeRepo;
	
	private static final Logger log = LoggerFactory.getLogger(TradeService.class);

	public boolean isValid(Trade trade){
		if(validateMaturityDate(trade)) {
			// Trade exsitingTrade = tradeDao.findTrade(trade.getTradeId());
			Optional<Trade> exsitingTrade = tradeRepo.findById(trade.getTradeId());
			if (exsitingTrade.isPresent()) {
				return validateVersion(trade, exsitingTrade.get());
			}else{
				return true;
			}
		}
		return false;
	}

	private boolean validateVersion(Trade trade,Trade oldTrade) {
		//validation 1  During transmission if the
		// lower version is being received by the store it will reject the trade and throw an exception.
		if(trade.getVersion() >= oldTrade.getVersion()){
			return true;
		}
		return false;
	}

	//2.	Store should not allow the trade which has less maturity date then today date
	private boolean validateMaturityDate(Trade trade){
		return trade.getMaturityDate().isBefore(LocalDate.now())  ? false:true;
	}

	public void  persist(Trade trade){
		// tradeDao.save(trade);
		trade.setCreatedDate(LocalDate.now());
		tradeRepo.save(trade);
	}

	public List<Trade> findAll(){
		return  tradeRepo.findAll();
		//return tradeDao.findAll();
	}

	public void updateExpiryFlagOfTrade(){
		/* tradeDao.tradeMap.forEach(
	               (k,v) -> {
	                   if(!validateMaturityDate(v)){
	                       v.setExpiredFlag("N");
	                       log.info("Trade which needs to updated {}",v);
	                   }
	               }
	       );*/
		tradeRepo.findAll().stream().forEach(t -> {
			if (!validateMaturityDate(t)) {
				t.setExpiredFlag("Y");
				log.info("Trade which needs to updated {}", t);
				tradeRepo.save(t);
			}
		});
	}
}
