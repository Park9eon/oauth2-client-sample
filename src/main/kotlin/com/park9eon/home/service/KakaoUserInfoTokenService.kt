package com.park9eon.home.service

import com.park9eon.home.model.ClientResources
import com.park9eon.home.model.UserAuthenticationDetails
import com.park9eon.home.model.social.KakaoUserInfo
import org.springframework.security.oauth2.client.OAuth2RestTemplate


/**
 * Initial version by: park9eon
 * Initial version created on: 05/04/2018
 */
class KakaoUserInfoTokenService(clientResources: ClientResources, oauth2RestTemplate: OAuth2RestTemplate) : OAuth2ClientUserInfoTokenService(clientResources, oauth2RestTemplate) {

    // 원본
    override fun getPrincipal(it: MutableMap<String, Any>): Any {
        return getKakaoUserInfo(it).kaccountEmail ?: UNKNOWN_FIELD
    }

    override fun convertAuthenticationDetails(details: Any): UserAuthenticationDetails {
        val kakaoUserInfo = getKakaoUserInfo(details)
        return UserAuthenticationDetails(
                kakaoUserInfo.id ?: UNKNOWN_FIELD,
                kakaoUserInfo.kaccountEmail ?: UNKNOWN_FIELD,
                "kakao")
                .apply {
                    this.profileName = kakaoUserInfo.properties?.nickname
                    this.profileName = kakaoUserInfo.properties?.profileImage
                }
    }

    private fun getKakaoUserInfo(map: Any) = objectMapper.convertValue(map, KakaoUserInfo::class.java)
}