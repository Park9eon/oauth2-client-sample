package com.park9eon.home.support

import com.park9eon.home.domain.User
import com.park9eon.home.service.UserService
import org.springframework.data.domain.AuditorAware
import java.util.*

open class AuditorAwareImpl(private val userService: UserService) : AuditorAware<User> {
    override fun getCurrentAuditor(): Optional<User> {
        return Optional.ofNullable(userService.currentUser())
    }
}