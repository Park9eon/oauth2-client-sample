package com.park9eon.home.model.content

import com.park9eon.home.model.user.User
import org.jetbrains.annotations.NotNull
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
) {

    @CreatedBy
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.EAGER)
    open lateinit var user: User

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    open lateinit var createdDate: Date

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    open lateinit var lastModifiedDate: Date

    @JoinColumn(name = "content_id")
    @ManyToOne(fetch = FetchType.LAZY)
    open lateinit var content: Content

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    open var childs: MutableSet<Comment>? = null

    @JoinColumn(name = "parent_id")
    @ManyToOne(fetch = FetchType.LAZY)
    open var parent: Comment? = null

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "comment")
    open var histories: MutableSet<Comment>? = null

    @Enumerated(EnumType.STRING)
    open lateinit var type: CommentType

    @Column(columnDefinition = "TEXT", nullable = false)
    open lateinit var source: String

    @NotNull
    open var enable = true
}