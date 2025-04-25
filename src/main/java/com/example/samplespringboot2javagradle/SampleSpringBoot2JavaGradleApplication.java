package com.example.samplespringboot2javagradle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SampleSpringBoot2JavaGradleApplication {

  public static void main(String[] args) {
    System.out.println("test!");
    SpringApplication.run(SampleSpringBoot2JavaGradleApplication.class, args);
  }
}
