package com.park9eon.home.repository

import com.park9eon.home.model.User
import org.springframework.data.repository.CrudRepository


/**
 * Initial version by: park9eon
 * Initial version created on: 05/04/2018
 */
interface UserRepository: CrudRepository<User, Long> {
    fun findByUsername(username: String): User
}