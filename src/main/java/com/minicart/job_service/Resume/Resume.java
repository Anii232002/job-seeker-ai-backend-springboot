package com.minicart.job_service.Resume;

import com.minicart.job_service.User.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Resume {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String textContent;
    private String filePath;

    private LocalDateTime uploadDate;

    @ElementCollection
    @CollectionTable(name = "resume_skills", joinColumns = @JoinColumn(name = "resume_id"))
    @Column(name = "skill")
    private List<String> parsedSkills;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String parsedSummary;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String parsedAnalysis;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    public Resume(User user, String textContent, String filePath, LocalDateTime uploadDate, List<String> parsedSkills, String parsedSummary) {
        this.user = user;
        this.textContent = textContent;
        this.filePath = filePath;
        this.uploadDate = uploadDate;
        this.parsedSkills = parsedSkills;
        this.parsedSummary = parsedSummary;
        this.parsedAnalysis = null;
    }





    public Resume() {

    }

    public String getParsedAnalysis() {
        return parsedAnalysis;
    }

    public void setParsedAnalysis(String parsedAnalysis) {
        this.parsedAnalysis = parsedAnalysis;
    }

    public Long getId() {
        return id;
    }


    public void setUser(User user) {
        this.user = user;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    public void setParsedSkills(List<String> parsedSkills) {
        this.parsedSkills = parsedSkills;
    }

    public void setParsedSummary(String parsedSummary) {
        this.parsedSummary = parsedSummary;
    }

    public User getUser() {
        return user;
    }

    public String getFilePath() {
        return filePath;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public List<String> getParsedSkills() {
        return parsedSkills;
    }

    public String getParsedSummary() {
        return parsedSummary;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public String getTextContent() {
        return textContent;
    }


}
