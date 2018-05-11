package com.park9eon.home.dao

import com.park9eon.home.domain.User
import com.park9eon.home.model.State
import com.park9eon.home.support.KpaRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable


/**
 * Initial version by: park9eon
 * Initial version created on: 05/04/2018
 */
interface UserRepository : KpaRepository<User, Long> {

    fun findByStatus(status: State): List<User>

    fun findByStatus(status: State, pageable: Pageable): Page<User>

    fun findByIdAndStatus(id: Long, status: State): User

    fun findByUsernameAndStatus(username: String, status: State): User

    @JvmDefault
    override fun findAll() =
            this.findByStatus(State.ENABLED)

    @JvmDefault
    override fun findAll(pageable: Pageable) =
            this.findByStatus(State.ENABLED, pageable)

    @JvmDefault
    override fun getOne(id: Long) =
            this.findByIdAndStatus(id, State.ENABLED)

    @JvmDefault
    fun findByUsername(username: String) =
            this.findByUsernameAndStatus(username, State.ENABLED)
}
