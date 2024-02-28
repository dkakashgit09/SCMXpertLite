package com.kafka.producer;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
@PropertySource("application.properties")
public class KafkaConfig 
{

	@Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

	@Value("${spring.kafka.producer.key-serializer}")
    private String keySerializer;

	@Value("${spring.kafka.producer.value-serializer}")
    private String valueSerializer;
	
    @Bean
    public ProducerFactory<String, String> producerFactory() 
    {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put("bootstrap.servers", bootstrapServers);
        configProps.put("key.serializer", keySerializer);
        configProps.put("value.serializer", valueSerializer);

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() 
    {
        return new KafkaTemplate<>(producerFactory());
    }
}
