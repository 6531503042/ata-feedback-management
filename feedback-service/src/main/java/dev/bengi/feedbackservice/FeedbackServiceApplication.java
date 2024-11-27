package dev.bengi.feedbackservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {
    "dev.bengi.feedbackservice.domain.model",
    "dev.bengi.feedbackservice.infrastructure.persistence.entity"
})
@EnableJpaRepositories(basePackages = "dev.bengi.feedbackservice.infrastructure.persistence.repository")
public class FeedbackServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(FeedbackServiceApplication.class, args);
    }
}