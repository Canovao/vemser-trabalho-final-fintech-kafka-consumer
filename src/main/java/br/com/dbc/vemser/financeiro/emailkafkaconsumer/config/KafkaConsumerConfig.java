package br.com.dbc.vemser.financeiro.emailkafkaconsumer.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value(value = "${kafka.bootstrap-servers}")
    private String bootstrapAddress;
    @Value(value = "${kafka.client-id}")
    private String clientId;

    @Value(value = "${kafka.properties.sasl.mechanism}")
    private String mechanism;
    @Value(value = "${kafka.properties.sasl.jaas.config}")
    private String config;
    @Value(value = "${kafka.properties.security.protocol}")
    private String protocol;
    @Value(value = "${kafka.properties.enable.idempotence}")
    private String idempotence;

    private static final String EARLIEST = "earliest";
    private static final String LATEST = "latest";

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> listenerContainerFactory(){
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, clientId);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 10);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, LATEST);
        props.put("sasl.mechanism", mechanism);
        props.put("sasl.jaas.config", config);
        props.put("security.protocol", protocol);
        props.put("enable.idempotence" , idempotence);

        DefaultKafkaConsumerFactory<Object, Object> kafkaConsumerFactory = new DefaultKafkaConsumerFactory<>(props);

        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(kafkaConsumerFactory);

        return factory;
    }

}
