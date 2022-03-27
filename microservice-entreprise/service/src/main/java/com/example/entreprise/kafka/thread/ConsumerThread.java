package com.example.entreprise.kafka.thread;

import com.example.entreprise.kafka.ConsumerKafka;
import com.example.entreprise.service.EmployeService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ConsumerThread implements CommandLineRunner {

    private ConsumerKafka consumerKafka;

    private BeanFactory beanFactory;

    public ConsumerThread(BeanFactory beanFactory) {
        this.consumerKafka = new ConsumerKafka();
        this.beanFactory = beanFactory;
    }

    @Override
    public void run(String... args) {
        Map<Integer, List<String>> messages = new HashMap<>();
        while (true) {
            ConsumerRecords<String, String> records = consumerKafka.consume();
            for (ConsumerRecord<String, String> record : records) {
                if (messages.get(Integer.parseInt(record.key())) == null) {
                    messages.put(Integer.parseInt(record.key()), new ArrayList<>());
                }
                messages.get(Integer.parseInt(record.key())).add(record.value());
                System.out.println(record.value());
            }
            for (int key : messages.keySet()) {
                this.beanFactory.getBean(EmployeService.class).addNotifications(key, messages.get(key));
            }
            messages.clear();
        }
    }
}
