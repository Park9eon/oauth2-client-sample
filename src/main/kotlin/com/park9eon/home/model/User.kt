package com.park9eon.home.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*
import javax.validation.constraints.NotNull


/**
 * Initial version by: park9eon
 * Initial version created on: 05/04/2018
 */
@Entity
open class User {
    @get:JsonIgnore
    @get:Id
    @get:GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null
    @get:Column(unique = true)
    open lateinit var username: String
    @get:JsonIgnore
    @get:NotNull
    open var enabled = true
    @get:JsonIgnore
    @get:OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER, mappedBy = "user")
    open var roles: Set<UserRole>? = null
    @get:OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER, mappedBy = "user")
    @get:JsonIgnore
    open var additions: Set<UserAddition>? = null
}