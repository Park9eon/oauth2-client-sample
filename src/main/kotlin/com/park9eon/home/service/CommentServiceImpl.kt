package com.park9eon.home.service

import com.park9eon.home.dao.CommentRepository
import com.park9eon.home.dao.ContentRepository
import com.park9eon.home.dao.UserRepository
import com.park9eon.home.domain.Comment
import com.park9eon.home.domain.Content
import com.park9eon.home.domain.User
import com.park9eon.home.model.CommentType
import com.park9eon.home.model.State
import com.park9eon.home.support.create
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class CommentServiceImpl(
        val userRepository: UserRepository,
        val commentRepository: CommentRepository,
        val contentRepository: ContentRepository
) : CommentService {

    @Transactional(readOnly = true)
    override fun getAll(page: Int, size: Int): Page<Comment> =
            this.commentRepository.findAll(PageRequest.of(page, size, Sort.by("createdDate")))

    @Transactional(readOnly = true)
    override fun getOne(id: Long): Comment =
            this.commentRepository.getOne(id)

    @Transactional(readOnly = true)
    override fun getOne(comment: Comment): Comment =
            this.commentRepository.getOne(comment.id)

    @Transactional
    override fun save(userId: Long, contentId: Long, source: String, type: CommentType): Comment =
            this.save(this.userRepository.getOne(userId), this.contentRepository.getOne(contentId), source, type)

    @Transactional
    override fun save(user: User, content: Content, source: String, type: CommentType): Comment =
            this._save(user, content, null, source, type)

    @Transactional
    override fun save(userId: Long, contentId: Long, parentId: Long, source: String, type: CommentType): Comment =
            this.save(this.userRepository.getOne(userId), this.contentRepository.getOne(contentId), this.commentRepository.getOne(parentId), source, type)

    @Transactional
    override fun save(user: User, content: Content, parent: Comment, source: String, type: CommentType): Comment =
            this._save(user, content, parent, source, type)

    private fun _save(user: User, content: Content, parent: Comment?, source: String, type: CommentType): Comment =
            this.commentRepository.create {
                this.user = user
                this.content = content
                this.source = source
                this.type = type
                this.parent = parent
            }

    @Transactional
    override fun update(id: Long, source: String): Comment =
            this.update(Comment(id), source)

    @Transactional
    override fun update(comment: Comment, source: String): Comment =
            this.commentRepository.save(this.commentRepository.getOne(comment.id)
                    .apply {
                        this.source = source
                    })

    @Transactional
    override fun delete(id: Long) =
            this.delete(this.commentRepository.getOne(id))

    @Transactional
    override fun delete(comment: Comment) {
        this.commentRepository.save(comment.apply {
            this.status = State.DELETED
        })
    }
}