package com.example.demo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;


@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
	SpringApplication.run(DemoApplication.class, args);
	   System.out.println("Welcome to CleanPick !");

	}
	//  @Configuration
    // public class WebMvcConfig implements WebMvcConfigurer {
    //     @Override
    //     public void addResourceHandlers(ResourceHandlerRegistry registry) {
    //         registry.addResourceHandler("/**")
    //                 .addResourceLocations("classpath:/static/")
    //                 .setCachePeriod(0); // Disable caching for development, adjust for production
    //     }
    // }

}
