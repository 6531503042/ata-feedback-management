package dev.bengi.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {
        "dev.bengi.userservice.domain.model",
        "dev.bengi.userservice.infrastructure.persistence.entity"
})
@EnableJpaRepositories(basePackages = {
        "dev.bengi.userservice.infrastructure.persistence.repository"
})
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

}
