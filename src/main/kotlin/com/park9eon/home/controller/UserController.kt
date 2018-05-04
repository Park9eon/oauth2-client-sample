package com.park9eon.home.controller

import com.park9eon.home.annotation.Me
import com.park9eon.home.domain.User
import com.park9eon.home.dao.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController


/**
 * Initial version by: park9eon
 * Initial version created on: 04/04/2018
 */
@RestController
@RequestMapping("/user")
class UserController {

    @Autowired
    lateinit var userRepository: UserRepository

    @RequestMapping(method = [RequestMethod.GET])
    fun me(@Me user: User) = user

    @RequestMapping("/{username}", method = [RequestMethod.GET])
    fun show(@PathVariable username: String) = userRepository.findByUsername(username)
}