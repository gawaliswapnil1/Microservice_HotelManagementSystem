package com.epam.reservation.swagger;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class DocConfiguration {

	@Bean
	public Docket swaggerConfiguration() {
		return new Docket(DocumentationType.SWAGGER_2)
					.select()
					.paths(PathSelectors.ant("/reservations/*"))
					.apis(RequestHandlerSelectors.basePackage("com.epam"))		
					.build()
					.apiInfo(apiInfo());
	}
	
	 private ApiInfo apiInfo() {
	      return new ApiInfo(
	              "Reservation Api",
	              "ReservationService for Reservation Reservation",
	              "V1",
	              "Free to Use",
	              new springfox.documentation.service.Contact("Swapnil", "www.epam.com","swapnil_gawali@epam.com"),
	              "Service Liencence",
	              "www.epam.com",
	              Collections.emptyList());
	  }
}