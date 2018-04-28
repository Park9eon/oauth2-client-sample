package com.park9eon.home.model.user

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*
import javax.validation.constraints.NotNull

/**
 * Initial version by: park9eon
 * Initial version created on: 05/04/2018
 */
@Entity
open class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long = 0
    @Column(unique = true)
    open lateinit var username: String
    @JsonIgnore
    @NotNull
    open var enabled = true
    @JsonIgnore
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER, mappedBy = "user")
    open var roles: MutableSet<UserRole>? = null
    @JsonIgnore
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER, mappedBy = "user")
    open var additions: MutableSet<UserAddition>? = null
}