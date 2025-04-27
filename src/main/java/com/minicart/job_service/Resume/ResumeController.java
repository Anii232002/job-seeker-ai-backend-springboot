package com.minicart.job_service.Resume;

import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {

    private ResumeService resumeService;

    @Autowired
    ResumeController(ResumeService resumeService){
        this.resumeService = resumeService;
    }

    @GetMapping("user/{userId}")
    public List<Resume> getUserResumes(@PathVariable Long userId){
        return this.resumeService.getUserResume(userId);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadResume(@RequestParam("userId") Long userId, @RequestParam("file") MultipartFile file) throws TikaException, IOException {
        try{
            resumeService.uploadResume(userId, file);
            return ResponseEntity.ok("Resume uploaded successfully");
        }catch(IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed");
        }

    }


    //here the param name id is different than the ResumeId named parm in the url hence the clarification
    //in the param
    @DeleteMapping(path="{ResumeId}")
    public void deleteResume(@PathVariable("ResumeId") Long id){
        this.resumeService.deleteResume(id);
    }


    //no need of clarification since url param name and param of the function both are same named
//    @PutMapping(path="/{id}")
//    public void updateResume(@PathVariable Long id){
//        this.resumeService.updateResume(id,Resume);
//    }
}
