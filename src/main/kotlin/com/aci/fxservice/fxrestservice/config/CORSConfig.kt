package com.aci.fxservice.fxrestservice.config
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsWebFilter
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource
import org.springframework.web.server.WebFilter

@Configuration
class CORSConfig {

    @Bean
    fun corsWebFilter(): WebFilter {
        val config = CorsConfiguration()
        // Not for production
        config.allowedOrigins = listOf("*") // Define the allowed origin here

        config.allowedMethods = listOf("GET", "POST", "PUT", "DELETE")
        config.allowedHeaders = listOf("*")

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", config)

        return CorsWebFilter(source)
    }
}
