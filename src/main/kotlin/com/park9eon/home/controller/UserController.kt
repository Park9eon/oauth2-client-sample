package com.park9eon.home.controller

import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal


/**
 * Initial version by: park9eon
 * Initial version created on: 04/04/2018
 */
@RestController
@RequestMapping
class UserController {

    @RequestMapping("/user", "/")
    fun show(principal: Principal?) = principal.let {(principal as? OAuth2Authentication)?.userAuthentication?.details
    }

}