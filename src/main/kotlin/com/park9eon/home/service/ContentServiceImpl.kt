package com.park9eon.home.service

import com.park9eon.home.extenstion.create
import com.park9eon.home.extenstion.get
import com.park9eon.home.extenstion.save
import com.park9eon.home.model.content.Content
import com.park9eon.home.model.content.ContentType
import com.park9eon.home.model.user.User
import com.park9eon.home.repository.ContentRepository
import com.park9eon.home.repository.UserRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class ContentServiceImpl(
        val userRepository: UserRepository,
        val contentRepository: ContentRepository
) : ContentService {

    @Transactional(readOnly = true)
    override fun findAll(page: Int, size: Int) =
            this.contentRepository.findAll(PageRequest.of(page, size, Sort.by("createdDate")))

    @Transactional(readOnly = true)
    override fun findById(id: Long) = this.contentRepository.get(id)

    @Transactional(readOnly = true)
    override fun findBy(content: Content): Content =
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
            this.contentRepository.save(content) {
                this.source = source
            }

    @Transactional
    override fun delete(id: Long) {
        this.delete(contentRepository.get(id))
    }

    @Transactional
    override fun delete(content: Content) {
        contentRepository.save(content) {
            enable = false
        }
    }

    @Transactional
    override fun connect(parentId: Long, childId: Long): Content =
            this.connect(contentRepository.get(parentId), contentRepository.get(parentId))

    @Transactional
    override fun connect(parent: Content, child: Content): Content =
            this.contentRepository.save(child) {
                this.parent = parent
            }

}