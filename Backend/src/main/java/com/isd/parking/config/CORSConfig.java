/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isd.parking.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Enable CORS access to resources from the frontend,
 * connection point between frontend SPA and backend API
 * specifies in a flexible way what kind of cross domain requests are authorized
 */
@Configuration
@Slf4j
public class CORSConfig implements WebMvcConfigurer {

    @Value("${front.url}")
    private String frontUrl;

    /**
     * Method specifies CORS mapping - which methods are allowed from allowed origins (frontend Angular SPA)
     *
     * @param registry - standard build-in CorsRegistry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        log.info("Call from front");

        registry.addMapping("/**")
                .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH")
                .allowedOrigins(frontUrl);  //TODO: change the URL for the prod URL when we deploy
    }
}
