package com.minicart.job_service.Kafka;

import java.time.LocalDateTime;

public class ResumeUploadedEvent {

    private Long resumeId;
    private Long userId;
    private String fileName;
    private LocalDateTime uploadedAt;

    private String textContent;

    public ResumeUploadedEvent() {
    }

    public ResumeUploadedEvent(Long resumeId, Long userId, String fileName, String textContent, LocalDateTime uploadedAt) {
        this.resumeId = resumeId;
        this.userId = userId;
        this.fileName = fileName;
        this.uploadedAt = uploadedAt;
        this.textContent = textContent;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public Long getResumeId() {
        return resumeId;
    }

    public void setResumeId(Long resumeId) {
        this.resumeId = resumeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
}
