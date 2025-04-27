package com.minicart.job_service.ATS;

import com.minicart.job_service.Resume.Resume;
import com.minicart.job_service.Resume.ResumeRepository;
import com.minicart.job_service.Service.ResumeAnalyzerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AtsService {

    private ResumeAnalyzerService resumeAnalyzerService;
    private ResumeRepository resumeRepository;

    @Autowired
    AtsService(ResumeAnalyzerService resumeAnalyzerService, ResumeRepository resumeRepository){
        this.resumeAnalyzerService = resumeAnalyzerService;
        this.resumeRepository = resumeRepository;
    }
    public String calculateATS(String jobDescription, Long resumeId)
    {
        Optional<Resume> resume = resumeRepository.findById(resumeId);
        if(resume.isEmpty()){
            throw new RuntimeException("resume not found with id %d".formatted(resumeId));
        }
        return resumeAnalyzerService.atsScoreResume(jobDescription,resume.get().getTextContent());
    }
}
