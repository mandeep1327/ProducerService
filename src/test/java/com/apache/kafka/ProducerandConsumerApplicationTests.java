package com.apache.kafka;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@EmbeddedKafka(topics = {"${kafka.topic_customer}", "${kafka.topic_product}"})
class ProducerandConsumerApplicationTests {

    @Test
    void contextLoads() {
        assertDoesNotThrow(() -> ProducerandConsumerApplication.main(new String[]{"--server.port=0"}));
    }

}
