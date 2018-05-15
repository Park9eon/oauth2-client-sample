package com.park9eon.home.service

import com.park9eon.home.domain.*
import org.springframework.data.domain.Page

/**
 * 기본값은 모든 사용자가 볼 수 있는 내용들로 세부 조건들을 주었을때 자세한 정보를 볼 수 있다.
 */
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

    fun deleteContentTag(contentTagId: Long)

    fun newCategory(name: String): Category

    fun updateCategory(category: Category): Category

    fun deleteCategory(categoryId: Long)

}