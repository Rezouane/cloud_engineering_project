package com.example.entreprise.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class ProducerKafka {
    private org.apache.kafka.clients.producer.Producer<String, String> producer;
    private static final String TOPIC_NAME = "projects";
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");

    public ProducerKafka() {
        Properties props = new Properties();
        props.put("bootstrap.servers", System.getenv("KAFKA_BROKER"));
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        producer = new KafkaProducer<String, String>(props);
    }

    public void sendMessage(int teamNumber, String message) {

        message = dateFormat.format(new Date()) + " - " + message;
        producer.send(new ProducerRecord<String, String>(TOPIC_NAME, String.valueOf(teamNumber), message));
        System.out.println("Message sent successfully");
    }
}
