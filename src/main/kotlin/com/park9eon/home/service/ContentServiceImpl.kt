package com.park9eon.home.service

import com.park9eon.home.dao.*
import com.park9eon.home.domain.*
import com.park9eon.home.model.State
import com.park9eon.home.support.create
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class ContentServiceImpl(
        val userService: UserService,
        val contentRepository: ContentRepository,
        val contentHistoryRepository: ContentHistoryRepository,
        val commentRepository: CommentRepository,
        val commentHistoryRepository: CommentHistoryRepository,
        val contentTagRepository: ContentTagRepository,
        val categoryRepository: CategoryRepository,
        val tagRepository: TagRepository
) : ContentService {

    @Transactional
    override fun getContents(userId: Long?, categoryId: Long?, offset: Int, size: Int): Page<Content> =
            PageRequest.of(offset, size)
                    .let { pageable ->
                        if (userId == null) {
                            if (categoryId == null) {
                                this.contentRepository.findByOrderByCreatedDateDesc(pageable)
                            } else {
                                this.contentRepository.findByCategoryOrderByCreatedDateDesc(Category(categoryId), pageable)
                            }
                        } else {
                            this.contentRepository.findByUser(User(userId), pageable)
                        }
                    }

    @Transactional
    override fun getContent(contentId: Long, userId: Long?): Content =
            (if (userId == null) {
                this.contentRepository.findByIdAndStatus(contentId, State.ENABLED)
            } else {
                this.contentRepository.getOne(contentId)
            })

    @Transactional
    override fun saveContent(content: Content): Content =
            this.contentRepository.save(content)

    @Transactional
    override fun updateContent(content: Content): Content =
            this.contentRepository.getOne(content.id)
                    .takeIf { it.user.id == userService.currentUser()?.id }
                    ?.let {
                        this.contentHistoryRepository.create {
                            this.content = it
                            this.title = it.title
                            this.source = it.source
                        }
                        this.contentRepository.save(content)
                    } ?: throw Exception()

    @Transactional
    override fun deleteContent(contentId: Long, force: Boolean) {
        this.contentRepository.getOne(contentId)
                .takeIf { it.user.id == userService.currentUser()?.id }
                ?.let { content ->
                    if (force) {
                        this.contentRepository.delete(content)
                    } else {
                        this.contentHistoryRepository.create {
                            this.content = content
                            this.title = content.title
                            this.source = content.source
                        }
                        content.status = State.DELETED
                        this.contentRepository.save(content)
                    }
                } ?: throw Exception()
    }

    @Transactional
    override fun getComments(contentId: Long, offset: Int, size: Int): Page<Comment> =
            this.commentRepository.findByContent_IdAndParentAndStatus(contentId, null, State.ENABLED, PageRequest.of(offset, size))

    @Transactional
    override fun saveComment(contentId: Long, comment: Comment): Comment =
            this.commentRepository.save(comment.apply {
                this.content = Content(contentId)
            })

    @Transactional
    override fun updateComment(comment: Comment): Comment {
        return this.commentRepository.getOne(comment.id)
                .takeIf { it.user?.id == userService.currentUser()?.id }
                ?.let {
                    this.commentRepository.save(comment)
                } ?: throw Exception()
    }

    @Transactional
    override fun deleteComment(commentId: Long, force: Boolean) {
        this.commentRepository.getOne(commentId)
                .takeIf { it.user?.id == userService.currentUser()?.id }
                ?.let { comment ->

                    if (force) {
                        this.commentRepository.delete(comment)
                    } else {
                        this.commentHistoryRepository.create {
                            this.comment = comment
                            this.source = comment.source
                        }
                        comment.status = State.DELETED
                        this.commentRepository.save(comment)
                    }
                } ?: throw Exception()
    }

    @Transactional
    override fun addContentTag(contentId: Long, tagName: String): ContentTag {
        val content = Content(contentId)
        val tag = try {
            tagRepository.getOne(tagName)
        } catch (e: Exception) {
            Tag(tagName)
        }
        val contentTag = this.contentTagRepository.findByContentAndTag(content, tag)
                ?: this.contentTagRepository.create {
                    this.content = Content(contentId)
                    this.tag = tag
                }
        return contentTag
    }

    @Transactional
    override fun deleteContentTag(contentId: Long, tagName: String) {
        this.contentTagRepository.deleteByContentAndTag_Name(Content(contentId), tagName)
    }

    @Transactional
    override fun newCategory(name: String): Category {
        return this.categoryRepository.create {
            this.name = name
        }
    }

    @Transactional
    override fun updateCategory(category: Category): Category {
        return this.categoryRepository.save(category)
    }

    @Transactional
    override fun deleteCategory(categoryId: Long) {
        this.categoryRepository.deleteById(categoryId)
    }

}