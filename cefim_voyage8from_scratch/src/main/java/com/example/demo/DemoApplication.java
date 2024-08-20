package com.example.demo;

import com.example.demo.fixture.InitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@SpringBootApplication
public class DemoApplication{

	//test
	@GetMapping("/")
	public String home() {
		return "Spring is here!";
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	@Profile("dev")
	CommandLineRunner commandLineRunner (InitService initService) {
		return (args) -> {
			log.info("***************** INIT SERVICE LOAD *****************");
			initService.initTravel();
			initService.initAccommodation();
			initService.initActivity();
			initService.initExtra();
			initService.initContact();
		};
	}
}
