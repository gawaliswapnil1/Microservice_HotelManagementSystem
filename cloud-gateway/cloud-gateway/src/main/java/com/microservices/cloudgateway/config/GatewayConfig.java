package com.microservices.cloudgateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
	 	@Autowired
	    AuthenticationFilter filter;

	    @Bean
	    public RouteLocator routes(RouteLocatorBuilder builder) {
	        return builder.routes()
	                .route("Guest-Service", r -> r.path("/guests/**")
	                        .filters(f -> f.filter(filter))
	                        .uri("lb://Guest-service"))
	                .route("Hotel-Service", r -> r.path("/hotels/**")
	                        .filters(f -> f.filter(filter))
	                        .uri("lb://Hotel-service"))
	                .route("Reservations-Service", r -> r.path("/reservations/createBooking")
	                        .filters(f -> f.filter(filter))
	                        .uri("lb://Reservation-service"))

	                .route("auth-service", r -> r.path("/auth/**")
	                        .filters(f -> f.filter(filter))
	                        .uri("lb://auth-service"))
	                .build();
	    }
}
