package com.park9eon.home.controller

import com.park9eon.home.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import java.security.Principal


/**
 * Initial version by: park9eon
 * Initial version created on: 04/04/2018
 */
@RestController
@RequestMapping("/user")
open class UserController {

    @Autowired
    lateinit var userRepository: UserRepository

    @RequestMapping(method = [RequestMethod.GET])
    open fun index() = userRepository.findAll()

    @RequestMapping("/me", method = [RequestMethod.GET])
    open fun me(@AuthenticationPrincipal principal: Principal?) = principal

    @RequestMapping("/{username}", method = [RequestMethod.GET])
    open fun show(@PathVariable username: String) = userRepository.findByUsername(username)
}