package com.park9eon.home.service

import com.park9eon.home.model.ClientResources
import com.park9eon.home.model.UserAuthenticationDetails
import com.park9eon.home.model.social.FacebookUserInfo
import org.springframework.security.oauth2.client.OAuth2RestTemplate


/**
 * Initial version by: park9eon
 * Initial version created on: 05/04/2018
 */
class FacebookUserInfoTokenService(clientResources: ClientResources, oauth2RestTemplate: OAuth2RestTemplate) : OAuth2ClientUserInfoTokenService(clientResources, oauth2RestTemplate) {

    // 원본
    override fun getPrincipal(it: MutableMap<String, Any>): Any {
        val facebookUserInfo = getFacebookUserInfo(it)
        return facebookUserInfo.email ?: UNKNOWN_FIELD
    }

    override fun convertAuthenticationDetails(details: Any): UserAuthenticationDetails {
        val facebookUserInfo = getFacebookUserInfo(details)
        return UserAuthenticationDetails(facebookUserInfo.id ?: UNKNOWN_FIELD,
                facebookUserInfo?.email ?: UNKNOWN_FIELD,
                "facebook")
                .apply {
                    this.profileName = facebookUserInfo.name
                    this.profileImageUrl = facebookUserInfo.picture?.data?.url
                }
    }

    private fun getFacebookUserInfo(map: Any) = objectMapper.readValue(objectMapper.writeValueAsString(map), FacebookUserInfo::class.java)
}