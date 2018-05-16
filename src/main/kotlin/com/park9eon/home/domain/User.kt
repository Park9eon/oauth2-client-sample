package com.park9eon.home.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.park9eon.home.model.State
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*
import javax.persistence.*

/**
 * Initial version by: park9eon
 * Initial version created on: 05/04/2018
 *
 * user.json
 * {
 *  id : number
 *  username : string
 *  create_date : string
 *  profile_name : string
 *  profile_picture : string?
 * }
 */
@Entity
@EntityListeners(AuditingEntityListener::class)
open class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        open var id: Long = 0
) {

    @get:JsonProperty
    @set:JsonIgnore
    @Column(unique = true, nullable = false)
    open lateinit var username: String

    @JsonIgnore
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    open var status: State = State.ENABLED

    @JsonIgnore
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER, mappedBy = "user")
    open var roles: MutableSet<UserRole>? = null

    @JsonIgnore
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER, mappedBy = "user")
    open var additions: MutableSet<UserAddition>? = null

    @set:JsonIgnore
    @get:JsonProperty("created_date")
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    open var createdDate: Date? = null

    @JsonIgnore
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    open var modifiedDate: Date? = null

    @get:Transient
    @get:JsonProperty("profile_name")
    val profileName: String
        get() = this.additions?.first()?.name ?: this.username

    @get:Transient
    @get:JsonProperty("profile_image")
    val profileImage: String?
        get() = this.additions?.first()?.imageUrl
}