package com.minicart.job_service.Kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private static final String TOPIC = "resume-uploaded-topic";

    private KafkaTemplate<String,Object> kafkaTemplate;

    @Autowired
    KafkaProducerService(KafkaTemplate<String,Object> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishResumeUploadedEvent(ResumeUploadedEvent event){
        kafkaTemplate.send(TOPIC,event);
    }
}
