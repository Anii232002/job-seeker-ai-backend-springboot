package com.minicart.job_service.Analyze;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.minicart.job_service.Resume.Resume;
import com.minicart.job_service.Resume.ResumeRepository;
import com.minicart.job_service.Service.ResumeAnalyzerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnalyzeService {

    private ResumeAnalyzerService resumeAnalyzerService;

    private ResumeRepository resumeRepository;

    @Autowired
    AnalyzeService(ResumeAnalyzerService resumeAnalyzerService, ResumeRepository resumeRepository){
        this.resumeAnalyzerService = resumeAnalyzerService;
        this.resumeRepository = resumeRepository;
    }
    public String analyzeResume(Long resumeId) {
        Resume resume = resumeRepository.findById(resumeId).orElseThrow(()->new RuntimeException(("Resume not found")));
        String resumeText = resume.getTextContent();
        if(resume.getParsedAnalysis()==null)
        {
            String res = resumeAnalyzerService.resumeAnalysis(resumeText);
            resume.setParsedAnalysis(res);
            JsonObject jsonObject = JsonParser.parseString(res).getAsJsonObject();
            resume.setParsedSummary(jsonObject.get("Summary").getAsString());
            JsonArray skillsArray = jsonObject.getAsJsonArray("Skills");

            List<String> skillsList = new ArrayList<>();
            for (JsonElement skillElement : skillsArray) {
                skillsList.add(skillElement.getAsString());
            }

            resume.setParsedSkills(skillsList);
            resumeRepository.save(resume);

            return res;
        }
        else{
            return resume.getParsedAnalysis();
        }



    }
}
