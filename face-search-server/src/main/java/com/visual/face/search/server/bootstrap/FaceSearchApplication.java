package com.visual.face.search.server.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@EnableScheduling
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class FaceSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(FaceSearchApplication.class, args);
	}

}
