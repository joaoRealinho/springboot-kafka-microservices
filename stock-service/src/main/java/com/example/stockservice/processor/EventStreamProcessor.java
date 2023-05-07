package com.example.stockservice.processor;

import com.example.basedomains.dto.OrderEvent;
import com.example.stockservice.kafkaStream.OrderConsumer;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Produced;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.support.serializer.JsonSerde;

@AllArgsConstructor
public class EventStreamProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);
    private StreamsBuilder streamsBuilder;

    /*@PostConstruct
    public void streamTopology() {
        KStream<String, OrderEvent> kStream = streamsBuilder.stream(topicName);
        //kStream.foreach((k, v) -> System.out.println("Key = " + k + " Value = " + v));
        kStream.mapValues((k, v) -> v.getStatus().equals("PENDING")).peek((k, v) -> System.out.println("Key = " + k + " Value = " + v)).to(topicName);
    }*/
    @Value("${spring.kafka.topic.name}")
    private String topicName;

    @PostConstruct
    public void process(StreamsBuilder builer) {

        // Serializes/deserializes
        final Serde<String> stringSerde = Serdes.String();
        final Serde<OrderEvent> orderEventSerde = new JsonSerde<>(OrderEvent.class);

        builer.stream(topicName, Consumed.with(stringSerde, orderEventSerde))
                .filter((k, v) -> v.getStatus().equals("PENDING"))
                .mapValues((k, v) -> new OrderEvent("Order is Complete", "APPROVED", v.getOrder()))
                .peek((k, v) -> LOGGER.info("Key = " + k + " Value = " + v))
                .to(topicName, Produced.with(stringSerde, orderEventSerde));
    }
}
