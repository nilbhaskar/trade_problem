package com.assessment.tradeFlow.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.assessment.tradeFlow.service.TradeService;

@Component
public class ScheduleTask {
	
	@Autowired
	private TradeService tradeService;

	private static final Logger log = LoggerFactory.getLogger(ScheduleTask.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Scheduled(cron = "${trade.expiry.schedule}")//currentlly setup 30 min
	public void reportCurrentTime() {
		log.info("The time is now {}", dateFormat.format(new Date()));
		tradeService.updateExpiryFlagOfTrade();
	}
}
