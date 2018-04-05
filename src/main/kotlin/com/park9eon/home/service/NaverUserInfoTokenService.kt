package com.park9eon.home.service

import com.park9eon.home.model.ClientResources
import com.park9eon.home.model.UserAuthenticationDetails
import com.park9eon.home.model.social.NaverUserInfo
import org.springframework.security.oauth2.client.OAuth2RestTemplate


/**
 * Initial version by: park9eon
 * Initial version created on: 05/04/2018
 */
class NaverUserInfoTokenService(clientResources: ClientResources, oauth2RestTemplate: OAuth2RestTemplate) : OAuth2ClientUserInfoTokenService(clientResources, oauth2RestTemplate) {

    // 원본
    override fun getPrincipal(it: MutableMap<String, Any>): Any {
        return getNaverUserInfo(it).response?.email ?: UNKNOWN_FIELD
    }

    override fun convertAuthenticationDetails(details: Any): UserAuthenticationDetails {
        val naverUserInfo = getNaverUserInfo(details).response
        return UserAuthenticationDetails(
                naverUserInfo?.id ?: UNKNOWN_FIELD,
                naverUserInfo?.email ?: UNKNOWN_FIELD,
                "naver")
                .apply {
                    this.profileName = naverUserInfo?.nickname
                    this.profileName = naverUserInfo?.profileImage
                }
    }

    private fun getNaverUserInfo(map: Any) = objectMapper.convertValue(map, NaverUserInfo::class.java)
}