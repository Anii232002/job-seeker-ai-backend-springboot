package com.minicart.job_service.ATS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ats")
public class AtsController {

    private final AtsService atsService;

    @Autowired
    AtsController(AtsService atsService){
        this.atsService = atsService;
    }
    @GetMapping(path = "/{resumeId}")
    public String atsScore(@RequestBody String jobDescription, @PathVariable Long resumeId){
        return atsService.calculateATS(jobDescription,resumeId);
    }
}
