package com.minicart.job_service.Prompts;

public enum PromptStore {
    ATS_SCORE_PROMPT("""
        You are an ATS system.
        Given a resume and a job description, calculate a match score (0-100).
        Return JSON with:
        - score
        - missingSkills (list of missing skills)
        
        Resume:
        %s
        
        Job Description:
        %s
    """),

    RESUME_ANALYSIS_PROMPT("""
                You're a resume analyzer AI. Extract key info:
                - Skills (only single json array with skills, no nesting just give technical skills in an json array)
                - Education
                - Summary (20-30 words)
                - Work Experience (nest in array of json objects)
                return in json format so it can be parsed. Dont leave any of the above requirements blank. 
                Resume:
                %s
                """);

    private final String promptText;

    PromptStore(String promptText) {
        this.promptText = promptText;
    }

    public String getPrompt() {
        return promptText;
    }

    }
