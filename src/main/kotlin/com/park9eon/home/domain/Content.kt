package com.park9eon.home.domain

import com.fasterxml.jackson.annotation.JsonIgnore
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
        open var id: Long = 0
) {

    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    open lateinit var user: User

    @Column(nullable = false)
    open lateinit var title: String

    @Column(columnDefinition = "TEXT", nullable = false)
    open lateinit var source: String

    @JsonIgnore
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    open lateinit var type: ContentType

    @JsonIgnore
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    open var status: State = State.DISABLED

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "content")
    open var comments: MutableSet<Comment>? = null

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "content")
    open var histories: MutableSet<ContentHistory>? = null

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "content")
    open var tags: MutableSet<ContentTag>? = null

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    open var createdDate: Date? = null

    @JsonIgnore
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    open var lastModifiedDate: Date? = null
}