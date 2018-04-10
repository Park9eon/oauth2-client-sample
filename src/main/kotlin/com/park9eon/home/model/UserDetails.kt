package com.park9eon.home.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority


/**
 * Initial version by: park9eon
 * Initial version created on: 06/04/2018
 */
class UserDetails : Authentication {

    constructor()

    constructor(user: User, details: Any) : this() {
        this.id = user.id
        this.authenticated = user.enabled
        this.username = user.username
        this.details = details
        this.authorities = user.roles?.map { SimpleGrantedAuthority(it.authority) }
    }

    var id: Long = 0
    private var username: String? = null
    private var credentials: Any? = null
    private var authorities: Collection<GrantedAuthority>? = null
    private var authenticated: Boolean = false
    private var details: Any? = null

    @JsonIgnore
    override fun getAuthorities() = this.authorities

    @JsonIgnore
    override fun isAuthenticated() = this.authenticated

    @JsonIgnore
    override fun setAuthenticated(isAuthenticated: Boolean) {
        this.authenticated = isAuthenticated
    }

    override fun getName() = this.username

    fun getUsername() = this.username

    @JsonIgnore
    override fun getCredentials() = this.credentials

    @JsonIgnore
    override fun getPrincipal() = this

    @JsonIgnore
    override fun getDetails() = this.details

}