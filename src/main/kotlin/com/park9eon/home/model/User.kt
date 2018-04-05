package com.park9eon.home.model

import javax.persistence.*


/**
 * Initial version by: park9eon
 * Initial version created on: 05/04/2018
 */
@Entity
open class User {
    @get:Id
    @get:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = -1L
    @get:Column(unique = true)
    lateinit var username: String
    @get:JoinColumn
    @get:OneToMany(cascade = [CascadeType.ALL])
    var roles: Set<UserRole>? = null
}