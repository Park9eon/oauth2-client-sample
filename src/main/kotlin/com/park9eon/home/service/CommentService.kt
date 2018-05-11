package com.park9eon.home.service

import com.park9eon.home.domain.Comment
import com.park9eon.home.model.CommentType
import com.park9eon.home.domain.Content
import com.park9eon.home.domain.User
import org.springframework.data.domain.Page

interface CommentService {

    fun getAll(page: Int, size: Int = 10): Page<Comment>

    fun getOne(id: Long): Comment

    fun getOne(comment: Comment): Comment

    fun save(userId: Long, contentId: Long, source: String, type: CommentType): Comment

    fun save(user: User, content: Content, source: String, type: CommentType): Comment

    fun save(userId: Long, contentId: Long, parentId: Long, source: String, type: CommentType): Comment

    fun save(user: User, content: Content, parent: Comment, source: String, type: CommentType): Comment

    fun update(id: Long, source: String): Comment

    fun update(comment: Comment, source: String): Comment

    fun delete(id: Long)

    fun delete(comment: Comment)

}