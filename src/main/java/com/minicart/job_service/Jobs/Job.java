package com.minicart.job_service.Jobs;

import java.math.BigInteger;


public class Job {

    private String jobId;
    private String title;
    private  String website;
    private String posted_at;
    private String company;
    private String location;
    private String description;
    private String applyLink;
    private BigInteger Salary;
    private Boolean remote;

    public Job() {
    }



    public Job(String jobId, String jobTitle, String employerWebsite, String jobPostedAtDatetimeUtc, String employerName, String jobLocation, String jobDescription, String jobApplyLink, boolean jobIsRemote) {
        this.jobId = jobId;
        this.title = jobTitle;
        this.website = employerWebsite;
        this.posted_at = jobPostedAtDatetimeUtc;
        this.company = employerName;
        this.location = jobLocation;
        this.description = jobDescription;
        this.applyLink = jobApplyLink;
        this.remote = jobIsRemote;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPosted_at() {
        return posted_at;
    }

    public void setPosted_at(String posted_at) {
        this.posted_at = posted_at;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getApplyLink() {
        return applyLink;
    }

    public void setApplyLink(String applyLink) {
        this.applyLink = applyLink;
    }

    public BigInteger getSalary() {
        return Salary;
    }

    public void setSalary(BigInteger salary) {
        Salary = salary;
    }

    public Boolean getRemote() {
        return remote;
    }

    public void setRemote(Boolean remote) {
        this.remote = remote;
    }
}
