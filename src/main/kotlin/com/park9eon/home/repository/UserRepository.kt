package com.park9eon.home.repository

import com.park9eon.home.model.user.User
import org.springframework.data.jpa.repository.JpaRepository


/**
 * Initial version by: park9eon
 * Initial version created on: 05/04/2018
 */
interface UserRepository: JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
}