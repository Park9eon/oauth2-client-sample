package com.park9eon.home.domain

import com.park9eon.home.model.Role
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
    @JoinColumn(name = "user_id", updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    open lateinit var user: User

    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    open lateinit var role: Role
}