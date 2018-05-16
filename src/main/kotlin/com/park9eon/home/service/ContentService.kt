package com.park9eon.home.service

import com.park9eon.home.domain.*
import org.springframework.data.domain.Page

interface ContentService {

    fun getContents(userId: Long? = null, categoryId: Long? = null, offset: Int, size: Int = 10): Page<Content>

    fun getContent(contentId: Long, userId: Long? = null): Content

    fun saveContent(content: Content): Content

    fun updateContent(content: Content): Content

    fun deleteContent(contentId: Long, force: Boolean = false)

    fun getComments(contentId: Long, offset: Int, size: Int = 10): Page<Comment>

    fun saveComment(contentId: Long, comment: Comment): Comment

    fun updateComment(comment: Comment): Comment

    fun deleteComment(commentId: Long, force: Boolean = false)

    fun addContentTag(contentId: Long, tagName: String): ContentTag

    fun deleteContentTag(contentId: Long, tagName: String)

    fun newCategory(name: String): Category

    fun updateCategory(category: Category): Category

    fun deleteCategory(categoryId: Long)

}