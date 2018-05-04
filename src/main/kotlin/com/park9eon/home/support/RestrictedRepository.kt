package com.park9eon.home.support

import com.park9eon.home.model.RestrictedEntity
import com.park9eon.home.model.State
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface RestrictedRepository<T : RestrictedEntity, R> : KpaRepository<T, R> {

    fun getAll(): MutableList<T> =
            findAllByStatus(State.ENABLE)

    override fun get(id: R): T {
        return this.findByIdAndStatus(id, State.ENABLE)
    }

    fun findByIdAndStatus(id: R, status: State): T

    fun findAllByStatus(status: State): MutableList<T>
}