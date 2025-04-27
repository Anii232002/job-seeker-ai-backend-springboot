package com.minicart.job_service.Resume;

import com.minicart.job_service.Kafka.KafkaProducerService;
import com.minicart.job_service.Kafka.ResumeUploadedEvent;
import com.minicart.job_service.User.User;
import com.minicart.job_service.User.UserRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ResumeService {

    private ResumeRepository resumeRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Autowired
    ResumeService(ResumeRepository resumeRepository, KafkaProducerService kafkaProducerService){
        this.resumeRepository = resumeRepository;
        this.kafkaProducerService = kafkaProducerService;
    }

    private static final String UPLOAD_DIR = "uploads/";

    @DeleteMapping
    public void deleteResume(Long id) {
        Optional<Resume> prod = this.resumeRepository.findById(id);
        if(prod.isEmpty()){
            new IllegalStateException("Resume with id "+id+" not found");
        }
        this.resumeRepository.deleteById(id);
    }


    @PostMapping
    public void uploadResume(Long userId, MultipartFile file) throws IOException, TikaException {
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("user not found"));

        String originalFileName = file.getOriginalFilename();
        String fileName = UUID.randomUUID()+"_"+originalFileName;
        Path filePath = Paths.get(UPLOAD_DIR+fileName);

        Files.createDirectories(filePath.getParent());
        Files.write(filePath,file.getBytes());

        String textContent = "";
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            PDFTextStripper stripper = new PDFTextStripper();
            textContent = stripper.getText(document);
            Resume resume = new Resume();
            resume.setUser(user);
            resume.setFilePath(filePath.toString());
            resume.setUploadDate(LocalDateTime.now());
            resume.setTextContent(textContent);

            resumeRepository.save(resume);
            ResumeUploadedEvent event = new ResumeUploadedEvent();
            event.setResumeId(resume.getId());
            event.setUserId(userId);
            event.setFileName(fileName);
            event.setUploadedAt(LocalDateTime.now());
            event.setTextContent(resume.getTextContent());

            kafkaProducerService.publishResumeUploadedEvent(event);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @GetMapping
    @Transactional(readOnly = true)
    public List<Resume> getUserResume(Long userId) {
        return this.resumeRepository.findByUserId(userId);
    }
}
