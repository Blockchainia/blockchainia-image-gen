package com.blockchainia;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ai.openai.*;
import org.springframework.ai.openai.api.*;
import org.springframework.ai.image.*;
import org.springframework.context.annotation.Primary;


@Configuration
public class AppConfiguration {

	//Define any one bean as per requirements

	// For OpenAI
	@Primary
	@Bean
	ImageModel imageModel(@Value("${spring.ai.openai.api-key}") String apiKey) {
	  return new OpenAiImageModel(new OpenAiImageApi(apiKey));
	}

	//For Stability
	// @Bean
	// ImageModel imageModel(@Value("${spring.ai.stability.api-key}") String apiKey) {
	//   return new StabilityAiImageModel(new StabilityAiApi(apiKey));
	// }
}