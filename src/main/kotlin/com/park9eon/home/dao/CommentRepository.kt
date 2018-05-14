package com.park9eon.home.dao

import com.park9eon.home.domain.Comment
import com.park9eon.home.model.State
import com.park9eon.home.support.KpaRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CommentRepository : KpaRepository<Comment, Long> {
    fun findByContent_IdAndParentAndStatus(contentId: Long, parent: Comment?, status: State = State.ENABLED, pageable: Pageable): Page<Comment>
}