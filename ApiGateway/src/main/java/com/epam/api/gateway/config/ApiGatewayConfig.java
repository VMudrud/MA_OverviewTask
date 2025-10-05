package com.epam.api.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfig {

    @Value("${song.service.gateway.uri}")
    private String songServiceUri;
    @Value("${resource.service.gateway.uri}")
    private String resourceServiceUri;

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("songService", r -> r.path("/songs/**").uri(songServiceUri))
                .route("resourceService", r -> r.path("/resources/**").uri(resourceServiceUri))
                .build();
    }
}
