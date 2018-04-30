package com.park9eon.home.service

import com.park9eon.home.model.content.Content
import com.park9eon.home.model.content.ContentType
import com.park9eon.home.model.user.User
import org.springframework.data.domain.Page

interface ContentService {

    fun findAll(page: Int, size: Int = 10): Page<Content>

    fun findById(id: Long): Content

    fun findBy(content: Content): Content

    fun save(userId: Long, source: String, type: ContentType = ContentType.TEXT): Content

    fun save(user: User, source: String, type: ContentType = ContentType.TEXT): Content

    fun update(id: Long, source: String): Content

    fun update(content: Content, source: String): Content

    fun delete(id: Long)

    fun delete(content: Content)

    fun connect(parentId: Long, childId: Long): Content

    fun connect(parent: Content, child: Content): Content

}