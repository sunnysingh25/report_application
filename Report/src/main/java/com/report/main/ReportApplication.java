package com.report.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@ComponentScans(value = { @ComponentScan("com.report.configuration"),
		@ComponentScan("com.report.model"),
		@ComponentScan("com.report.main"),
		@ComponentScan("com.report.controller"),
		@ComponentScan("com.report.miscallaneous"),
		@ComponentScan("com.report.security"),
		@ComponentScan("com.report.dao"),
		@ComponentScan("com.report.service")})

public class ReportApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(ReportApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ReportApplication.class);
	}
	
	

}
