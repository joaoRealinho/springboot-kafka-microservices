package com.example.stockservice.kafkaStream;


import com.example.basedomains.dto.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.support.serializer.JsonSerde;

@Configuration
@EnableKafka
@EnableKafkaStreams
@Slf4j
public class KafkaStreamConfig {

    @Value("${spring.kafka.topic.name}")
    private String topicName;

    @Bean
    public StreamsConfig streamsConfig(KafkaProperties properties) {
        return new StreamsConfig(properties.buildStreamsProperties());
    }

    @Autowired
    public void process(StreamsBuilder builer) {
        // Serializes/deserializes
        final Serde<String> stringSerde = Serdes.String();
        final Serde<OrderEvent> orderEventSerde = new JsonSerde<>(OrderEvent.class);

        builer.stream(topicName, Consumed.with(stringSerde, orderEventSerde)).filter((k, v) -> v.getStatus().equals("PENDING")).mapValues((k, v) -> new OrderEvent("Order is Complete", "APPROVED", v.getOrder())).peek((k, v) -> log.info("Value = " + v)).to(topicName, Produced.with(stringSerde, orderEventSerde));
    }
}