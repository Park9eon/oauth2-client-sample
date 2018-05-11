package com.park9eon.home.service

import com.park9eon.home.domain.Content
import com.park9eon.home.model.ContentType
import com.park9eon.home.domain.User
import org.springframework.data.domain.Page

interface ContentService {

    fun getAll(page: Int, size: Int = 10): Page<Content>

    fun getOne(id: Long): Content

    fun getOne(content: Content): Content

    fun save(userId: Long, source: String, type: ContentType): Content

    fun save(user: User, source: String, type: ContentType): Content

    fun update(userId: Long, id: Long, source: String): Content

    fun update(user: User, content: Content, source: String): Content

    fun delete(userId: Long, id: Long)

    fun delete(user: User, content: Content)

    fun modifiable(user: User, content: Content): Boolean
}