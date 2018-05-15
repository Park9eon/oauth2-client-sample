package com.park9eon.home.domain

import com.park9eon.home.model.ContentType
import com.park9eon.home.model.State
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*
import javax.persistence.*

/**
 * Initial version by: park9eon
 * Initial version created on: 28/04/2018
 */
@Entity
@EntityListeners(AuditingEntityListener::class)
open class Content(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(updatable = false, nullable = false)
        open var id: Long = 0
) {

    @CreatedBy
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    open lateinit var user: User

    @Column(nullable = false)
    open lateinit var title: String

    @Column(columnDefinition = "TEXT")
    open lateinit var source: String

    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    open lateinit var type: ContentType

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    open var contentCategory: Category? = null

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    open var status: State = State.DISABLED

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "content")
    open var comments: MutableSet<Comment>? = null

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "content")
    open var histories: MutableSet<ContentHistory>? = null

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "content")
    open var tags: MutableSet<ContentTag>? = null

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    open var createdDate: Date? = null

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    open var lastModifiedDate: Date? = null

}