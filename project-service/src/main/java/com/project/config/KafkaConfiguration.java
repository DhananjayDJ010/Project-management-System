package com.project.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;



//import com.fasterxml.jackson.databind.JsonSerializer;
//import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import com.project.dto.User;

/*@Configuration
public class KafkaConfiguration {
    @Bean
	public ProducerFactory<String,User> producerFactory(){
		
		Map<String, Object> map = new HashMap<>();
		
		map.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		map.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		map.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		
		return new DefaultKafkaProducerFactory<>(map);
	}
    @Bean
    public KafkaTemplate<String, User> kafkaTemplate(){
    	return new KafkaTemplate<>(producerFactory());
    }
    
    @Bean
    public NewTopic topicExample() {
      return TopicBuilder.name("my-first-kafka-topic")
        .partitions(6)
        .replicas(3)
        .build();
    }
    
    @Bean
    public NewTopic topicExample1() {
      return TopicBuilder.name("my-second-kafka-topic")
        .partitions(3)
        .replicas(2)
        .build();
    }
}*/
