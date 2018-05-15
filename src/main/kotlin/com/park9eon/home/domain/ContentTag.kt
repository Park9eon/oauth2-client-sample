package com.park9eon.home.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*
import javax.persistence.*


/**
 * Initial version by: park9eon
 * Initial version created on: 30/04/2018
 */
@Entity
@EntityListeners(AuditingEntityListener::class)
open class ContentTag(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0
) {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id", nullable = false, updatable = false)
    open lateinit var content: Content

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", nullable = false)
    open lateinit var tag: Tag

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    open var createdDate: Date? = null

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    open var lastModifiedDate: Date? = null
}