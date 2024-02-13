package com.scm.kafka.consumer;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Component
@PropertySource("application.properties")
public class DataConsumer 
{
	@Value("spring.data.mongodb.uri")
	private String mongoUri;
	@Value("spring.data.mongodb.database")
	private String mongoDatabase;

	private String mongoCollection="data";

	MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017/SCMXpertLite");
    MongoDatabase mongodatabase = mongoClient.getDatabase("SCMXpertLite");
	private MongoCollection<Document> mongocollection =mongodatabase.getCollection(mongoCollection);
    
    @KafkaListener(topics = "streamdata", groupId = "group_id")
    public void receive(String message) 
    {
        System.out.println("Received message: " + message);
        Document documentdata = Document.parse(message);
        mongocollection.insertOne(documentdata);
        System.out.println("Insertion done...");
    }
}
