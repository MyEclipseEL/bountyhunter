package com.example;

import org.apache.http.client.methods.HttpHead;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;

@SpringBootApplication
@ServletComponentScan( basePackages = "com.example.*.*")
@ComponentScan( basePackages = "com.example.*")
@EnableAutoConfiguration
public class BountyhunterApplication {

	public static void main(String[] args) {
		SpringApplication.run(BountyhunterApplication.class, args);
	}

//	private CorsConfiguration buildConfig(){
//		CorsConfiguration corsConfiguration = new CorsConfiguration();
//		corsConfiguration.addAllowedOrigin("*");
//		corsConfiguration.addAllowedHeader("*");
//		corsConfiguration.addAllowedMethod("*");
//		corsConfiguration.addExposedHeader(HttpHeaderConStant.X_TOTAL_COUNT);
//
//	}

}
