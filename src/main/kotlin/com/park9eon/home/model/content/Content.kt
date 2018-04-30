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
open class Content(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        open var id: Long = 0
) {

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.EAGER)
    open lateinit var user: User

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "content")
    open var comments: MutableSet<Comment>? = null

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    open var childs: MutableSet<Content>? = null

    @JoinColumn(name = "parent_id")
    @ManyToOne(fetch = FetchType.EAGER)
    open var parent: Content? = null

    @Enumerated(EnumType.STRING)
    open lateinit var type: ContentType

    @Column(columnDefinition = "TEXT")
    open lateinit var source: String

    @NotNull
    open var enable = true

    @CreatedBy
    open lateinit var createdBy: String

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    open lateinit var createdDate: Date

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    open lateinit var lastModifiedDate: Date
}