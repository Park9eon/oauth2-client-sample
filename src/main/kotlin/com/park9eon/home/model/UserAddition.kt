package com.park9eon.home.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*
import javax.validation.constraints.NotNull


/**
 * Initial version by: park9eon
 * Initial version created on: 08/04/2018
 */
@Entity
open class UserAddition {
    @get:Id
    @get:GeneratedValue(strategy = GenerationType.IDENTITY)
    @get:JsonIgnore
    open var id: Long? = null
    @get:NotNull
    @get:JoinColumn(name = "user_id")
    @get:ManyToOne(fetch = FetchType.LAZY)
    @get:JsonIgnore
    open lateinit var user: User
    @get:NotNull
    @get:JsonIgnore
    open lateinit var source: String
    @get:NotNull
    open lateinit var name: String
    open var imageUrl: String? = null
    @get:Column(columnDefinition = "TEXT")
    @get:JsonIgnore
    open var details: String? = null
}