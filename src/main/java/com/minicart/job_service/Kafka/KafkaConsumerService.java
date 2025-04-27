package com.minicart.job_service.Kafka;

import com.minicart.job_service.Analyze.AnalyzeService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private AnalyzeService analyzerService;

    KafkaConsumerService( AnalyzeService analyzerService){
        this.analyzerService = analyzerService;
    }
    @KafkaListener(topics = "resume-uploaded-topic", groupId = "resume-group")
    public void consume(ResumeUploadedEvent event){
        System.out.println("Received resume upload event"+event.getResumeId());
        analyzerService.analyzeResume(event.getResumeId());
    }
}
