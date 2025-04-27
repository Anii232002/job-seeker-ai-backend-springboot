package com.minicart.job_service.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minicart.job_service.Jobs.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class JobService {
    private final WebClient.Builder webClientBuilder;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RedisTemplate<String, List<Job>> redisTemplate;

    @Autowired
    JobService(WebClient.Builder webClientBuilder, RedisTemplate<String, List<Job>> redisTemplate){
        this.webClientBuilder = webClientBuilder;
        this.redisTemplate = redisTemplate;
    }

    @Value("${jsearch.api.key}")
    private String apiKey;

    public List<Job> searchJobs(String query) {

        String redisKey = "jobs::"+query.toLowerCase();

        List<Job> cacheJobs = redisTemplate.opsForValue().get(redisKey);
        if(cacheJobs!=null && !cacheJobs.isEmpty()){
            return cacheJobs;
        }
        WebClient webClient = webClientBuilder.baseUrl("https://jsearch.p.rapidapi.com").build();

        String response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search")
                        .queryParam("query", query)
                        .queryParam("page", 1)
                        .queryParam("num_pages", 1)
                        .queryParam("country", "in")
                        .queryParam("date_posted", "today")
                        .build())
                .header("x-rapidapi-host", "jsearch.p.rapidapi.com")
                .header("x-rapidapi-key", apiKey)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        List<Job> jobs = extractJobsFromResponse(response);

        redisTemplate.opsForValue().set(redisKey,jobs, Duration.ofDays(1));

        return jobs;
    }

    private List<Job> extractJobsFromResponse(String response) {
        List<Job> jobs = new ArrayList<>();
        try {
            JsonNode root = objectMapper.readTree(response);
            JsonNode dataArray = root.path("data");

            if (dataArray.isArray()) {
                for (JsonNode node : dataArray) {

                    Job job = new Job(
                            node.path("job_id").asText(),
                            node.path("job_title").asText(),
                            node.path("employer_website").asText(),
                            node.path("job_posted_at_datetime_utc").asText(),
                            node.path("employer_name").asText(),
                            node.path("job_location").asText(),
                            node.path("job_description").asText(),
                            node.path("job_apply_link").asText(),
                            node.path("job_is_remote").asBoolean()
                    );
                    jobs.add(job);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();  // you can handle error properly too
        }

        return jobs;
    }
}
