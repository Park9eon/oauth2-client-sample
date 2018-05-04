package com.park9eon.home.dao

import com.park9eon.home.domain.User
import com.park9eon.home.model.State
import com.park9eon.home.support.RestrictedRepository


/**
 * Initial version by: park9eon
 * Initial version created on: 05/04/2018
 */
interface UserRepository: RestrictedRepository<User, Long> {

    fun findByUsername(username: String): User? =
            findByUsernameAndStatus(username, State.ENABLE)

    fun findByUsernameAndStatus(username: String, status: State): User?
}