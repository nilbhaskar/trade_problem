package com.assessment.tradeFlow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TradeFlowApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradeFlowApplication.class, args);
	}

}
