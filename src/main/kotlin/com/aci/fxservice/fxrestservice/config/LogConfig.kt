package com.aci.fxservice.fxrestservice.config

import com.aci.fxservice.fxrestservice.logging.ILogger
import com.aci.fxservice.fxrestservice.logging.Logger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class LogConfig {

    @Bean
    fun getLogger(): ILogger {
        return Logger()
    }
}
