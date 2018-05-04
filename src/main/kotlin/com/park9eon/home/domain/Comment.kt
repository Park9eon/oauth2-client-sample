package com.park9eon.home.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.park9eon.home.model.CommentType
import com.park9eon.home.model.RestrictedEntity
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
open class Comment(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        open var id: Long = 0
) : RestrictedEntity {

    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    open lateinit var user: User

    @JsonIgnore
    @JoinColumn(name = "content_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    open lateinit var content: Content

    @Column(columnDefinition = "TEXT", nullable = false)
    open lateinit var source: String

    @JsonIgnore
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    open lateinit var type: CommentType

    @JsonIgnore
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    override var status: State? = State.ENABLE

    @JsonIgnore
    @JoinColumn(name = "parent_id")
    @ManyToOne(fetch = FetchType.LAZY)
    open var parent: Comment? = null

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    open var childs: MutableSet<Comment>? = null

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "comment")
    open var histories: MutableSet<Comment>? = null

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    open var createdDate: Date? = null

    @JsonIgnore
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    open var lastModifiedDate: Date? = null


}