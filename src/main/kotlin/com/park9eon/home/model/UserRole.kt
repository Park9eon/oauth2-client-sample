package com.park9eon.home.model

import javax.persistence.*
import javax.validation.constraints.NotNull


/**
 * Initial version by: park9eon
 * Initial version created on: 05/04/2018
 */
@Entity
open class UserRole {

    companion object {
        const val ROLE_USER = "ROLE_USER"
        const val ROLE_ADMIN = "ROLE_ADMIN"
    }

    @get:Id
    @get:GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null
    @get:NotNull
    @get:JoinColumn(name = "user_id")
    @get:ManyToOne(fetch = FetchType.LAZY)
    open lateinit var user: User
    @get:NotNull
    open lateinit var authority: String

    constructor()

    constructor(user: User, authority: String) {
        this.user = user
        this.authority = authority
    }
}