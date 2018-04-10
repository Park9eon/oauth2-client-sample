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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    open var id: Long = 0
    @NotNull
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    open lateinit var user: User
    @NotNull
    @JsonIgnore
    open lateinit var source: String
    @NotNull
    open var name: String? = null
    open var imageUrl: String? = null
    @Column(columnDefinition = "TEXT")
    @JsonIgnore
    open var details: String? = null
}