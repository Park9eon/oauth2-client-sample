package com.park9eon.home.service

import com.park9eon.home.dao.ContentHistoryRepository
import com.park9eon.home.dao.ContentRepository
import com.park9eon.home.dao.UserRepository
import com.park9eon.home.domain.Content
import com.park9eon.home.domain.User
import com.park9eon.home.model.ContentType
import com.park9eon.home.model.State
import com.park9eon.home.support.create
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class ContentServiceImpl(
        val userRepository: UserRepository,
        val contentRepository: ContentRepository,
        val contentHistoryRepository: ContentHistoryRepository
) : ContentService {

    @Transactional(readOnly = true)
    override fun getAll(page: Int, size: Int): Page<Content> =
            this.contentRepository.findAll(PageRequest.of(if (page > 0) page - 1 else 0, size, Sort.by("createdDate")))

    @Transactional(readOnly = true)
    override fun get(id: Long) = this.contentRepository.get(id)

    @Transactional(readOnly = true)
    override fun get(content: Content): Content =
            this.contentRepository.get(content.id)

    @Transactional
    override fun save(userId: Long, source: String, type: ContentType): Content =
            this.save(userRepository.get(userId), source, type)

    @Transactional
    override fun save(user: User, source: String, type: ContentType): Content =
            this.contentRepository.create {
                this.source = source
                this.type = type
                this.user = user
            }

    @Transactional
    override fun update(id: Long, source: String): Content =
            this.update(contentRepository.get(id), source)

    @Transactional
    override fun update(content: Content, source: String): Content =
            this.contentHistoryRepository.create {
                this.content = content
                this.source = content.source
            }.let {
                this.contentRepository.save(content) {
                    this.source = source
                }
            }

    @Transactional
    override fun delete(id: Long) {
        this.delete(contentRepository.get(id))
    }

    @Transactional
    override fun delete(content: Content) {
        contentRepository.save(content) {
            this.status = State.DELETED
        }
    }

}