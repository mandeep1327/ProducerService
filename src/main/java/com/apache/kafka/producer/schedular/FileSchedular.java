package com.apache.kafka.producer.schedular;

import com.apache.kafka.producer.service.ports.ReadFile;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;


@Configuration
@EnableScheduling
public class FileSchedular {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileSchedular.class);

	private ReadFile service;

	public FileSchedular(ReadFile service)
	{
		this.service = service;
	}

	@Scheduled(cron = "${producer.loader.ping}")
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
	@Scheduled(cron = "${producer.loader.ping}")
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
