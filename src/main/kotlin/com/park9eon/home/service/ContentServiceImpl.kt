package com.park9eon.home.service

import com.park9eon.home.dao.ContentHistoryRepository
import com.park9eon.home.dao.ContentRepository
import com.park9eon.home.dao.ContentTagRepository
import com.park9eon.home.dao.UserRepository
import com.park9eon.home.domain.Content
import com.park9eon.home.domain.User
import com.park9eon.home.model.ContentType
import com.park9eon.home.model.Role
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
        val contentHistoryRepository: ContentHistoryRepository,
        val contentTagRepository: ContentTagRepository
) : ContentService {

    @Transactional(readOnly = true)
    override fun getAll(page: Int, size: Int): Page<Content> =
            this.contentRepository.findAll(PageRequest.of(if (page > 0) page else 1, size, Sort.by("createdDate")))

    @Transactional(readOnly = true)
    override fun getOne(id: Long) = this.contentRepository.getOne(id)

    @Transactional(readOnly = true)
    override fun getOne(content: Content): Content =
            this.contentRepository.getOne(content.id)

    @Transactional
    override fun save(userId: Long, source: String, type: ContentType): Content =
            this.save(userRepository.getOne(userId), source, type)

    @Transactional
    override fun save(user: User, source: String, type: ContentType): Content =
            this.contentRepository.create {
                this.source = source
                this.type = type
                this.user = user
            }

    @Transactional
    override fun update(userId: Long, id: Long, source: String): Content =
            this.update(this.userRepository.getOne(userId), this.contentRepository.getOne(id), source)

    @Transactional
    override fun update(user: User, content: Content, source: String): Content =
            if (this.modifiable(user, content)) {
                this.contentHistoryRepository.create {
                    this.content = content
                    this.source = content.source
                }.let {
                    this.contentRepository.save(content) {
                        this.source = source
                    }
                }
            } else {
                throw Exception()
            }

    @Transactional
    override fun delete(userId: Long, id: Long) {
        this.delete(this.userRepository.getOne(id), this.contentRepository.getOne(id))
    }

    @Transactional
    override fun delete(user: User, content: Content) {
        this.contentRepository.save(content) {
            this.status = State.DELETED
        }
    }

    override fun modifiable(user: User, content: Content): Boolean {
        return user.id == content.user.id || user.roles?.map { it.role }?.contains(Role.ROLE_ADMIN)?:false
    }
}