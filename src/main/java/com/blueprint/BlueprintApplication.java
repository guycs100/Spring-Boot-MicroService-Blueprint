package com.blueprint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class BlueprintApplication {

	private static ApplicationContext appContext;

	public static void main(String[] args) {
		appContext = SpringApplication.run(BlueprintApplication.class, args);
	}

	public static ApplicationContext getAppContext() {
		return appContext;
	}
}
