package com.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.kafka.producer.SendMessage;

@SpringBootApplication
public class KafkaProducerApplication 
{
	@Autowired
	private static SendMessage sendMessage;
	
	public static void main(String[] args) 
	{
		ApplicationContext context = SpringApplication.run(KafkaProducerApplication.class, args);
		sendMessage =context.getBean(SendMessage.class);
		sendMessage.sendMessageToTopic();
	}

}
