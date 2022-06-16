package com.epam.hotel.swagger;

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

	/*
	 * @Bean public Docket swaggerHotelsConfiguration() { return new
	 * Docket(DocumentationType.SWAGGER_2) .groupName("Hotel") .select()
	 * .paths(PathSelectors.ant("/hotels/**"))
	 * .apis(RequestHandlerSelectors.basePackage("com.epam")) .build()
	 * .apiInfo(apiInfo()); }
	 */
	@Bean
	public Docket swaggerRoomsConfiguration() {
		return new Docket(DocumentationType.SWAGGER_2)
					.groupName("Room")
					.select()
					.paths(PathSelectors.ant("/rooms/**"))
					.apis(RequestHandlerSelectors.basePackage("com.epam"))		
					.build()
					.apiInfo(apiInfo());
	}

	/*
	 * @Bean public Docket swaggerCategoryConfiguration() { return new
	 * Docket(DocumentationType.SWAGGER_2) .groupName("Category") .select()
	 * .paths(PathSelectors.ant("/category/**"))
	 * .apis(RequestHandlerSelectors.basePackage("com.epam")) .build()
	 * .apiInfo(apiInfo()); }
	 */
	 private ApiInfo apiInfo() {
	      return new ApiInfo(
	              "Hotel Api",
	              "HotelService for Hotel Reservation",
	              "V1",
	              "Free to Use",
	              new springfox.documentation.service.Contact("Swapnil", "www.epam.com","swapnil_gawali@epam.com"),
	              "Service Liencence",
	              "www.epam.com",
	              Collections.emptyList());
	  }
}
