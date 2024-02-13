package com.scm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scm.kafka.producer.SendMessage;

@Component
public class DataRetrievalInitializer {

    @Autowired
    private SendMessage sendMessage;

//    @PostConstruct
    public void initializeDataRetrieval() {
//        sendMessage.sendMessageToTopic();
    }
}

