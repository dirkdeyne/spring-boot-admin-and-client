package com.example.demo;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



public class DemoApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication admin = new SpringApplication(DemoAdminServer.class);
		admin.setAdditionalProfiles("admin");
		admin.run(args);

		SpringApplication client = new SpringApplication(DemoClient.class);
		client.setAdditionalProfiles("client");
		client.run(args);
	}
}

@EnableAdminServer
@Configuration
@EnableAutoConfiguration
@Profile("admin")
class DemoAdminServer {

}

@SpringBootApplication
@RestController
@Profile("client")
class DemoClient {

	@GetMapping
	public String hello(){
		return "Hello world!";
	}

}
