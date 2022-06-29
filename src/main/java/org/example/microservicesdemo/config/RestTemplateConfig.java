package org.example.microservicesdemo.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){
       return builder.setConnectTimeout(Duration.ofMillis(10000))
             .setReadTimeout(Duration.ofMillis(10000)).additionalMessageConverters(new MappingJackson2HttpMessageConverter()).additionalMessageConverters(new MappingJackson2HttpMessageConverter())
             .build();

    }



}
