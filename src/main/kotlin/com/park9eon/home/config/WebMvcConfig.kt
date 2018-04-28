package com.park9eon.home.config

import com.park9eon.home.annotation.MeArgumentResolver
import com.park9eon.home.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


/**
 * Initial version by: park9eon
 * Initial version created on: 28/04/2018
 */
@Configuration
open class WebMvcConfig : WebMvcConfigurer {

    @Autowired
    lateinit var userService: UserService

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(MeArgumentResolver(this.userService))
    }
}