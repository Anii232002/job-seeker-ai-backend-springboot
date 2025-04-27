package com.minicart.job_service.Jobs;

import com.minicart.job_service.Service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/jobs")
public class JobsController {

    private JobService jobService;

    @Autowired
    JobsController(JobService jobService){
        this.jobService = jobService;
    }
    @GetMapping
    public List<Job> getJobs(@RequestParam String query){
        return jobService.searchJobs(query);
    }
}
