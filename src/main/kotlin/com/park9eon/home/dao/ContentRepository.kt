package com.park9eon.home.dao

import com.park9eon.home.domain.Category
import com.park9eon.home.domain.Content
import com.park9eon.home.domain.User
import com.park9eon.home.model.State
import com.park9eon.home.support.KpaRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ContentRepository : KpaRepository<Content, Long> {

    fun findByOrderByCreatedDateDesc(pageable: Pageable): Page<Content>

    fun findByCategoryOrderByCreatedDateDesc(category: Category, pageable: Pageable): Page<Content>

    fun findByUser(user: User, pageable: Pageable): Page<Content>

    fun findByIdAndStatus(id: Long, status: State = State.ENABLED): Content

}
