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
    var id: Long = -1L
    @get:NotNull
    @get:JoinColumn
    @get:ManyToOne(fetch = FetchType.LAZY)
    lateinit var user: User
    @get:NotNull
    lateinit var role: String
}