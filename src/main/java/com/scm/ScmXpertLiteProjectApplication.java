package com.scm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.scm.kafka.producer.SendMessage;

@SpringBootApplication
public class ScmXpertLiteProjectApplication 
{
	
	@Autowired
	private static SendMessage sendMessage;

	public static void main(String[] args) 
	{
		ApplicationContext context = SpringApplication.run(ScmXpertLiteProjectApplication.class, args);
		sendMessage =context.getBean(SendMessage.class);
		sendMessage.sendMessageToTopic();
	}

}
