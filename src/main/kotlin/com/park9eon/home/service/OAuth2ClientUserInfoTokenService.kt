package com.park9eon.home.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.park9eon.home.model.ClientResources
import com.park9eon.home.model.UserAuthenticationDetails
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.security.oauth2.provider.OAuth2Authentication


/**
 * Initial version by: park9eon
 * Initial version created on: 05/04/2018
 */
abstract class OAuth2ClientUserInfoTokenService(clientResources: ClientResources, oauth2RestTemplate: OAuth2RestTemplate)
    : UserInfoTokenServices(clientResources.resource.userInfoUri, clientResources.client.clientId) {
    init {
        this.setRestTemplate(oauth2RestTemplate)
    }

    companion object {
        const val UNKNOWN_FIELD = "unknown"
    }

    val objectMapper = jacksonObjectMapper()

    override fun loadAuthentication(accessToken: String?): OAuth2Authentication {
        return super.loadAuthentication(accessToken).apply {
            (this.userAuthentication as UsernamePasswordAuthenticationToken)
                    .apply {
                        this.details = this.details.let {
                            convertAuthenticationDetails(it)
                        }
                    }
        }
    }

    abstract fun convertAuthenticationDetails(details: Any): UserAuthenticationDetails
}