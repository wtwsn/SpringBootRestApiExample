package com.websystique.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication(scanBasePackages={"com.websystique.springboot"})// same as @Configuration @EnableAutoConfiguration @ComponentScan combined
public class SpringBootRestApiApp {

	public static void main(String[] args) {
		System.out.println("--------begin112-----");
		System.out.println(" ");
		SpringApplication.run(SpringBootRestApiApp.class, args);
		System.out.println("------end112---------");
	}
}
