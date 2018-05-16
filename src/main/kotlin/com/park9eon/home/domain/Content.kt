package com.park9eon.home.domain

import com.fasterxml.jackson.annotation.*
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
 *
 * content.json
 * {
 *  id : number
 *  user_id : number
 *  user_name: string
 *  user_image: string
 *  title : string
 *  source : string
 *  tags : string[]?
 *  category_id : number?
 *  type : string
 *  created_date : date
 * }
 */
@Entity
@EntityListeners(AuditingEntityListener::class)
open class Content(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(updatable = false, nullable = false)
        open var id: Long = 0
) {

    @JsonIdentityReference
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator::class, property = "user_id")
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

    @JsonIdentityReference
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator::class, property = "category_id")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    open var contentCategory: Category? = null

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

    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], mappedBy = "content")
    open var tags: MutableSet<ContentTag>? = null

    @set:JsonIgnore
    @get:JsonProperty("created_date")
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