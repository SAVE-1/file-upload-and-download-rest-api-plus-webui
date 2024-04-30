package com.filesharing.filebin;

import com.filesharing.filebin.file.filestorage.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@SpringBootApplication
public class Application {
	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		logger.info("http://localhost:8080/swagger-ui.html");
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}
}
