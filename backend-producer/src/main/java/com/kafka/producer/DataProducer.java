package com.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class DataProducer 
{
    private final KafkaTemplate<String, String> kafkaTemplate;

    public DataProducer(KafkaTemplate<String, String> kafkaTemplate) 
    {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message) 
    {
    	kafkaTemplate.send("streamdata", message);
    }
}
