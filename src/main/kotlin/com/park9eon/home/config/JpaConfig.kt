package com.park9eon.home.config

import com.park9eon.home.service.UsernameAuditorAware
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
open class JpaConfig {
    @Bean
    open fun auditorAware(): AuditorAware<String> {
        return UsernameAuditorAware()
    }
}
