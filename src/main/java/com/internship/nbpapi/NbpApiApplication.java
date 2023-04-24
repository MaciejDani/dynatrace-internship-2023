package com.internship.nbpapi;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class NbpApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(NbpApiApplication.class, args);
	}

	@Bean("nbpRestTemplate")
	public RestTemplate nbpRestTemplate(@Value("${nbp.url}") String url) {
		return new RestTemplateBuilder()
				.rootUri(url)
				.build();
	}
}
