package com.park9eon.home.model.user

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

/**
 * Initial version by: park9eon
 * Initial version created on: 06/04/2018
 */
class UserDetails(
        var id: Long? = null,
        private var name: String? = null,
        private var authenticated: Boolean = false,
        private var authorities: Collection<GrantedAuthority>? = null
) : Authentication {

    constructor(user: User) : this(user.id, user.username, user.enabled, user.roles?.map { SimpleGrantedAuthority(it.role.name) })

    @JsonIgnore
    override fun getAuthorities() = this.authorities

    @JsonIgnore
    override fun isAuthenticated() = this.authenticated

    @JsonIgnore
    override fun setAuthenticated(isAuthenticated: Boolean) {
        this.authenticated = isAuthenticated
    }

    @JsonIgnore
    override fun getName() = this.name

    @JsonIgnore
    override fun getCredentials() = null

    @JsonIgnore
    override fun getPrincipal() = this

    @JsonIgnore
    override fun getDetails() = null

}