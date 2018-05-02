package com.park9eon.home.model.content

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
open class CommentHistory(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0
) {

    @JoinColumn(name = "comment_id")
    @ManyToOne(fetch = FetchType.EAGER)
    open lateinit var comment: Comment

    @Column(columnDefinition = "TEXT", nullable = false)
    open lateinit var source: String

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    open lateinit var createdDate: Date

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    open lateinit var lastModifiedDate: Date
}