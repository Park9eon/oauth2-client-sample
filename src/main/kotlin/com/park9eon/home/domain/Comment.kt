package com.park9eon.home.domain

import com.fasterxml.jackson.annotation.*
import com.park9eon.home.model.CommentType
import com.park9eon.home.model.State
import org.hibernate.annotations.BatchSize
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Size

/**
 * Initial version by: park9eon
 * Initial version created on: 28/04/2018
 *
 * comment.json
 * {
 *  id : number
 *  user_id: number
 *  user_name: string
 *  user_image: string
 *  created_date: string
 *  childes: object[]
 *  type: string
 *  source : string
 * }
 */
@Entity
@EntityListeners(AuditingEntityListener::class)
open class Comment(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        open var id: Long = 0
) {

    @JsonIdentityReference
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator::class, property = "user_id")
    @CreatedBy
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    open lateinit var user: User

    @JsonIgnore
    @JoinColumn(name = "content_id", updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    open lateinit var content: Content

    @Column(columnDefinition = "TEXT")
    open var source: String? = null

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    open lateinit var type: CommentType

    @JsonIgnore
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    open var status: State = State.ENABLED

    @JsonIgnore
    @JoinColumn(name = "parent_id")
    @ManyToOne(fetch = FetchType.LAZY)
    open var parent: Comment? = null

    @BatchSize(size = 10)
    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], mappedBy = "parent")
    open var childs: MutableSet<Comment>? = null

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "comment")
    open var histories: MutableSet<CommentHistory>? = null

    @get:JsonProperty("created_date")
    @set:JsonIgnore
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    open var createdDate: Date? = null

    @JsonIgnore
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    open var lastModifiedDate: Date? = null

    @get:Transient
    @get:JsonProperty("user_name")
    val userProfileName: String
        get() = this.user.additions?.first()?.name ?: this.user.username

    @get:Transient
    @get:JsonProperty("user_image")
    val userProfileImage: String?
        get() = this.user.additions?.first()?.imageUrl
}