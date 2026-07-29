package com.alamara.ecommerce.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaPaymentTopicConfirmation {

    public NewTopic paymentTopic(){
        return TopicBuilder.name("payment-topic").build();
    }
}
