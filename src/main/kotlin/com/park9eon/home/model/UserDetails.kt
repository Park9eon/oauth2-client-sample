package com.park9eon.home.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority


/**
 * Initial version by: park9eon
 * Initial version created on: 06/04/2018
 */
data class UserDetails(private val details: Any) : User(), Authentication {

    constructor(user: User, details: Any) : this(details) {
        this.id = user.id
        this.username = user.username
        this.enabled = user.enabled
        this.additions = user.additions
        this.roles = user.roles
    }

    @JsonIgnore
    override fun getAuthorities() = this.roles?.map { SimpleGrantedAuthority(it.authority) }

    @JsonIgnore
    override fun setAuthenticated(isAuthenticated: Boolean) {
        this.enabled = isAuthenticated
    }

    override fun getName() = this.additions?.last()?.name

    fun getImageUrl() = this.additions?.last()?.imageUrl

    @JsonIgnore
    override fun getCredentials() = authorities

    @JsonIgnore
    override fun getPrincipal() = this

    @JsonIgnore
    override fun isAuthenticated() = this.enabled

    @JsonIgnore
    override fun getDetails() = this.details

}