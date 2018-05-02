package com.park9eon.home.service

import com.park9eon.home.model.content.Comment
import com.park9eon.home.model.content.CommentType
import com.park9eon.home.model.content.Content
import com.park9eon.home.model.user.User
import org.springframework.data.domain.Page

interface CommentService {

    fun findAll(page: Int, size: Int = 10): Page<Comment>

    fun findById(id: Long): Comment

    fun findBy(comment: Comment): Comment

    fun save(userId: Long, contentId: Long, source: String, type: CommentType = CommentType.TEXT): Comment

    fun save(user: User, content: Content, source: String, type: CommentType = CommentType.TEXT): Comment

    fun update(id: Long, source: String): Comment

    fun update(comment: Comment, source: String): Comment

    fun delete(id: Long)

    fun delete(comment: Comment)

    fun connect(parentId: Long, childId: Long): Comment

    fun connect(parent: Comment, child: Comment): Comment

}