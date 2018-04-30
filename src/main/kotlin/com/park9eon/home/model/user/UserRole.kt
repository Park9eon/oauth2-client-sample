package com.park9eon.home.model.user

import javax.persistence.*
import javax.validation.constraints.NotNull


/**
 * Initial version by: park9eon
 * Initial version created on: 05/04/2018
 */
@Entity
open class UserRole(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        open var id: Long = 0
) {
    @NotNull
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    open lateinit var user: User
    @NotNull
    @Enumerated(EnumType.STRING)
    open lateinit var role: Role
}