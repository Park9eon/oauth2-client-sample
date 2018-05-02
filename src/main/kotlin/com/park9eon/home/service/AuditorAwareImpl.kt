package com.park9eon.home.service

import com.park9eon.home.model.user.User
import org.springframework.data.domain.AuditorAware
import java.util.*

open class AuditorAwareImpl(private val userService: UserService) : AuditorAware<User> {
    override fun getCurrentAuditor(): Optional<User> {
        return Optional.ofNullable(userService.currentUser())
    }
}