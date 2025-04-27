package com.minicart.job_service.Analyze;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analyze")
public class AnalyzeController {

    @Autowired
    private AnalyzeService analyzeService;
    AnalyzeController(){

    }

    @GetMapping(path = "/{resumeId}")
    public ResponseEntity<String> analyzeResume(@PathVariable Long resumeId){
        return ResponseEntity.ok(this.analyzeService.analyzeResume(resumeId));

    }
}
