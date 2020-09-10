package com.shaoxing.lixing;

import com.shaoxing.lixing.global.config.docs.JApiDocsConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.shaoxing.lixing.mapper")
public class LixingApplication {

	public static void main(String[] args) {
		SpringApplication.run(LixingApplication.class, args);
		JApiDocsConfig.generateDocs();
	}

}
