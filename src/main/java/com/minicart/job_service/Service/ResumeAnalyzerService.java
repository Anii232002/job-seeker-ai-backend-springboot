package com.minicart.job_service.Service;

import com.minicart.job_service.Prompts.PromptStore;
import io.github.sashirestela.openai.SimpleOpenAI;
import io.github.sashirestela.openai.domain.chat.ChatMessage;
import io.github.sashirestela.openai.domain.chat.ChatRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ResumeAnalyzerService {
    private final SimpleOpenAI openAI;

    @Autowired
    public ResumeAnalyzerService(@Value("${openai.api.key}") String apiKey){

        this.openAI = SimpleOpenAI.builder()
                .apiKey(apiKey)
                .build();

    }

    public String atsScoreResume(String jobDescription, String resumeContent){
        String prompt  = PromptStore.ATS_SCORE_PROMPT.getPrompt();
        prompt = prompt.formatted(jobDescription,resumeContent);
        return askGpt(prompt);
    }

    public String resumeAnalysis(String resumeContent){
        String prompt  = PromptStore.RESUME_ANALYSIS_PROMPT.getPrompt();
        prompt = prompt.formatted(resumeContent);
        return askGpt(prompt);
    }

    private String askGpt(String prompt){

        ChatRequest request = ChatRequest.builder().model("gpt-3.5-turbo").message(ChatMessage.UserMessage.of(prompt))
                .temperature(0.7)
                .build();
        var futureChat = openAI.chatCompletions().create(request);
        var chatResponse = futureChat.join();
        return chatResponse.firstContent();
    }

}
