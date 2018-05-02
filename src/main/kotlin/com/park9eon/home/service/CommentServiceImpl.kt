package com.park9eon.home.service

import com.park9eon.home.extenstion.create
import com.park9eon.home.extenstion.get
import com.park9eon.home.model.content.Comment
import com.park9eon.home.model.content.CommentType
import com.park9eon.home.model.content.Content
import com.park9eon.home.model.user.User
import com.park9eon.home.repository.CommentRepository
import com.park9eon.home.repository.ContentRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
open class CommentServiceImpl(
        val commentRepository: CommentRepository,
        val contentRepository: ContentRepository
) : CommentService {

    override fun findAll(page: Int, size: Int): Page<Comment> =
            this.commentRepository.findAll(PageRequest.of(page, size, Sort.by("createdDate")))

    override fun findById(id: Long): Comment =
            this.commentRepository.get(id)

    override fun findBy(comment: Comment): Comment =
            this.commentRepository.get(comment.id)

    override fun save(userId: Long, contentId: Long, source: String, type: CommentType): Comment =
            this.save(User(userId), Content(contentId), source, type)

    override fun save(user: User, content: Content, source: String, type: CommentType): Comment =
            this.commentRepository.create {
                this.user = user
                this.content = content
                this.source = source
                this.type = type
            }

    override fun update(id: Long, source: String): Comment =
            this.update(Comment(id), source)

    override fun update(comment: Comment, source: String): Comment =
            this.commentRepository.save(commentRepository.get(comment.id)
                    .apply {
                        this.source = source
                    })

    override fun delete(id: Long) =
            this.delete(Comment(id))

    override fun delete(comment: Comment) {
        comment.enable = false
        this.commentRepository.save(comment)
    }

    override fun connect(parentId: Long, childId: Long): Comment {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun connect(parent: Comment, child: Comment): Comment {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}