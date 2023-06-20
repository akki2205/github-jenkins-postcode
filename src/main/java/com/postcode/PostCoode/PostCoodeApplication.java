package com.postcode.PostCoode;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class PostCoodeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostCoodeApplication.class, args);
	}
	
	@Bean
	public RestTemplate getTempltae() {
		return new RestTemplate();
	}
	@Bean
	public ModelMapper modelMapper() {
		
		return new ModelMapper();
	}
}
