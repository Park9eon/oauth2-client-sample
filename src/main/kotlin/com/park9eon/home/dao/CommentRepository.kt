package com.park9eon.home.dao

import com.park9eon.home.domain.Comment
import com.park9eon.home.model.State
import com.park9eon.home.support.KpaRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CommentRepository : KpaRepository<Comment, Long> {
    fun findByStatus(status: State = State.ENABLED): List<Comment>

    fun findByStatus(status: State = State.ENABLED, pageable: Pageable): Page<Comment>

    fun findByIdAndStatus(id: Long, status: State = State.ENABLED): Comment

    @JvmDefault
    override fun getOne(id: Long) = this.findByIdAndStatus(id)

    @JvmDefault
    fun findAll(status: State = State.ENABLED) =
            this.findByStatus(status)

    @JvmDefault
    fun findAll(status: State = State.ENABLED, pageable: Pageable) =
            this.findByStatus(State.ENABLED, pageable)
}