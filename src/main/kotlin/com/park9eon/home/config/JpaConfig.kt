package com.park9eon.home.config

import com.park9eon.home.model.user.User
import com.park9eon.home.service.AuditorAwareImpl
import com.park9eon.home.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
open class JpaConfig {
    @Autowired
    lateinit var userService: UserService

    @Bean
    open fun auditorAware(): AuditorAware<User> {
        return AuditorAwareImpl(userService)
    }
}
