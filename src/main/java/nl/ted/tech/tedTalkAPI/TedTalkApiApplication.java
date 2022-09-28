package nl.ted.tech.tedTalkAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableCaching
@EntityScan(basePackages = {"nl.ted.tech.tedTalkAPI.model"} )
@EnableJpaRepositories(basePackages = {"nl.ted.tech.tedTalkAPI.repository"})
public class TedTalkApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TedTalkApiApplication.class, args);
    }

//    @Bean
//    public GroupedOpenApi tedTalkApiV1() {
//        return GroupedOpenApi.builder()
//                .group("tedTalk-api-1.0")
//                .pathsToMatch("/tedTalk//v1.0/**")
//                .build();
//    }
//
//    @Bean
//    public GroupedOpenApi tedTalkApiV11() {
//        return GroupedOpenApi.builder()
//                .group("tedTalk-api-1.1")
//                .pathsToMatch("/tedTalk/v1.1/**")
//                .build();
//    }

}
