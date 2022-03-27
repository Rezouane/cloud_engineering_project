package com.example.entreprise.kafka;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.*;

public class ConsumerKafka {

    private KafkaConsumer<String, String> consumer;
    private static final String TOPIC_NAME = "projects";

    public ConsumerKafka() {
        String bootstrapServers = System.getenv("KAFKA_BROKER");
        String grp_id = "third_app";
        //Creating consumer properties
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, grp_id);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        //creating consumer
        consumer = new KafkaConsumer<String, String>(properties);
        //Subscribing
        consumer.subscribe(Arrays.asList(TOPIC_NAME));
    }

    public Map<Integer, List<String>> getMessages() {
        Map<Integer, List<String>> messages = new HashMap<>();
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
        for (ConsumerRecord<String, String> record : records) {
            if (messages.get(Integer.parseInt(record.key())) == null) {
                messages.put(Integer.parseInt(record.key()), new ArrayList<>());
            }
            messages.get(Integer.parseInt(record.key())).add(record.value());
            System.out.println(record.value());
        }
        return messages;
    }

    public ConsumerRecords<String, String> consume() {
        return consumer.poll(Duration.ofMillis(100));
    }
}
