package org.example.kafka;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

public class SyncProducerDemo {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        props.put(ProducerConfig.ACKS_CONFIG, "all");       // 确保所有副本确认写入
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringSerializer");
        for(int i = 0;i<10;i++) {
            try (Producer<String, String> producer = new KafkaProducer<>(props)) {
                ProducerRecord<String, String> record =
                        new ProducerRecord<>("test-topic", "key"+i, "Hello Kafka!");

                producer.send(record, (recordMetadata, e) -> System.out.printf("Sent to partition %d, offset %d%n",
                        recordMetadata.partition(), recordMetadata.offset()));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
