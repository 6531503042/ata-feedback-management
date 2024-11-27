package dev.bengi.feedbackservice.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "dev.bengi.feedbackservice.infrastructure.client")
public class FeignConfig {
} 