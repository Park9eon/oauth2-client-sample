package com.park9eon.home.repository

import com.park9eon.home.model.User
import com.park9eon.home.model.UserAddition
import org.springframework.data.repository.CrudRepository


/**
 * Initial version by: park9eon
 * Initial version created on: 08/04/2018
 */
interface UserAdditionRepository: CrudRepository<UserAddition, Long> {
    fun findOneByUserAndSource(user: User, source: String): UserAddition?
}