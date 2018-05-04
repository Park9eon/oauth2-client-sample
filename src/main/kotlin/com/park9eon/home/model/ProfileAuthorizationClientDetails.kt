package com.park9eon.home.model

import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails


/**
 * Initial version by: park9eon
 * Initial version created on: 26/04/2018
 */
open class ProfileAuthorizationClientDetails : AuthorizationCodeResourceDetails() {
    lateinit var clientName: String
    lateinit var processesUrl: String
}