package com.park9eon.home.service

import com.park9eon.home.model.ClientResources
import com.park9eon.home.model.UserAuthenticationDetails
import com.park9eon.home.model.social.GoogleUserInfo
import org.springframework.security.oauth2.client.OAuth2RestTemplate


/**
 * Initial version by: park9eon
 * Initial version created on: 05/04/2018
 */
class GoogleUserInfoTokenService(clientResources: ClientResources, oauth2RestTemplate: OAuth2RestTemplate) : OAuth2ClientUserInfoTokenService(clientResources, oauth2RestTemplate) {

    // 원본
    override fun getPrincipal(it: MutableMap<String, Any>): Any {
        return getGoogleUserInfo(it).email ?: UNKNOWN_FIELD
    }

    override fun convertAuthenticationDetails(details: Any): UserAuthenticationDetails {
        val googleUserInfo = getGoogleUserInfo(details)
        return UserAuthenticationDetails(
                googleUserInfo.sub ?: UNKNOWN_FIELD,
                googleUserInfo.email ?: UNKNOWN_FIELD,
                "google")
                .apply {
                    this.profileName = googleUserInfo.name
                    this.profileName = googleUserInfo.picture
                }
    }

    private fun getGoogleUserInfo(map: Any) = objectMapper.convertValue(map, GoogleUserInfo::class.java)
}