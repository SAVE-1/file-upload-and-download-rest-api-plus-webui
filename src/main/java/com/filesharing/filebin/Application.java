package com.filesharing.filebin;

import com.filesharing.filebin.controller.FileUploadControllerLocalStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
	private static final Logger logger = LoggerFactory.getLogger(FileUploadControllerLocalStorage.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		logger.info("http://localhost:8080/swagger-ui.html");
	}

//	@Bean
//	CommandLineRunner init(StorageService storageService) {
//		return (args) -> {
//			storageService.deleteAll();
//			storageService.init();
//		};
//	}
}
