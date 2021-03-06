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
open class ContentHistory(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0
) {

    @JoinColumn(name = "content_id", nullable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    open lateinit var content: Content

    @Column(nullable = false, updatable = false)
    open lateinit var title: String

    @Column(columnDefinition = "TEXT", nullable = false, updatable = false)
    open lateinit var source: String

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    open var createdDate: Date? = null

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    open var lastModifiedDate: Date? = null
}