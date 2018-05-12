package com.park9eon.home.dao

import com.park9eon.home.domain.Content
import com.park9eon.home.model.State
import com.park9eon.home.support.KpaRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ContentRepository : KpaRepository<Content, Long> {

    fun findByStatus(status: State = State.ENABLED): List<Content>

    fun findByStatus(status: State = State.ENABLED, pageable: Pageable): Page<Content>

    fun findByIdAndStatus(id: Long, status: State = State.ENABLED): Content

    @JvmDefault
    fun findAll(status: State = State.ENABLED) =
            this.findByStatus(status)

    @JvmDefault
    fun findAll(status: State = State.ENABLED, pageable: Pageable) =
            this.findByStatus(State.ENABLED, pageable)

    @JvmDefault
    override fun getOne(id: Long) =
            this.findByIdAndStatus(id)
}
