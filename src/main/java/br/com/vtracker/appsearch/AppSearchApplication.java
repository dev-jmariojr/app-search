package br.com.vtracker.appsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AppSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppSearchApplication.class, args);
	}

}
