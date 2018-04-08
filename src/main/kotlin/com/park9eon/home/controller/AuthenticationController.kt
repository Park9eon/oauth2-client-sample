package com.park9eon.home.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.security.Principal


/**
 * Initial version by: park9eon
 * Initial version created on: 06/04/2018
 */
@Controller
@RequestMapping
open class AuthenticationController {

    @GetMapping("/auth")
    open fun login(model: Model): String {
        return "auth"
    }

    @RequestMapping("/auth/callback")
    open fun callback(principal: Principal): Principal {
        return principal
    }

}