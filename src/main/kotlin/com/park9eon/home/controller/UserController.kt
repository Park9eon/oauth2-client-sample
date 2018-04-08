package com.park9eon.home.controller

import com.park9eon.home.model.User
import com.park9eon.home.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


/**
 * Initial version by: park9eon
 * Initial version created on: 04/04/2018
 */
@RestController
@RequestMapping("/user")
open class UserController {

    @Autowired
    lateinit var userRepository: UserRepository

    @GetMapping
    open fun index() = userRepository.findAll()

    @GetMapping("/me")
    open fun show(@AuthenticationPrincipal user: User?) = user

    @GetMapping("/{username}")
    open fun show(@PathVariable username: String) = userRepository.findByUsername(username)
}