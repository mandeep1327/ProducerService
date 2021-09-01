package com.apache.kafka.producer.inbound;

import com.apache.kafka.producer.service.ports.LoadDataService;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;


@Configuration
@EnableScheduling
public class SchedularController {
	private static final Logger LOGGER = LoggerFactory.getLogger(SchedularController.class);

	private LoadDataService service;

	public SchedularController(LoadDataService service)
	{
		this.service = service;
	}

	@Scheduled(cron = "${producer.loader.testing}")
	public void loadCustomer()  {
		try {
			service.readCustomerFile();
		}
		catch(IOException e){
			LOGGER.info("No File Found");
		}
		catch(ParseException e){
			LOGGER.error("File Format Not Correct");
		}
	}
	@Scheduled(cron = "${producer.loader.testing}")
	public void loadProduct() {
		try {
		service.readProductFile();
		}
		catch(IOException e){
			LOGGER.info("No File Found");
		}
		catch(ParseException e){
			LOGGER.error("File Format Not Correct");
		}
	}
}
