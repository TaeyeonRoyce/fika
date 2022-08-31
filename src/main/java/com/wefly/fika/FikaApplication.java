package com.wefly.fika;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FikaApplication {
	public static void main(String[] args) {
		SpringApplication.run(FikaApplication.class, args);
	}

}
