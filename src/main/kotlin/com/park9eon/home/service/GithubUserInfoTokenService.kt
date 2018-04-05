package com.park9eon.home.service

import com.park9eon.home.model.ClientResources
import com.park9eon.home.model.UserAuthenticationDetails
import com.park9eon.home.model.social.GithubUserInfo
import org.springframework.security.oauth2.client.OAuth2RestTemplate


/**
 * Initial version by: park9eon
 * Initial version created on: 05/04/2018
 */
class GithubUserInfoTokenService(clientResources: ClientResources, oauth2RestTemplate: OAuth2RestTemplate) : OAuth2ClientUserInfoTokenService(clientResources, oauth2RestTemplate) {

    // 원본
    override fun getPrincipal(it: MutableMap<String, Any>): Any {
        return getGithubUserInfo(it).email ?: UNKNOWN_FIELD
    }

    override fun convertAuthenticationDetails(details: Any): UserAuthenticationDetails {
        val githubUserInfo = getGithubUserInfo(details)
        return UserAuthenticationDetails(
                githubUserInfo.id ?: UNKNOWN_FIELD,
                githubUserInfo.email ?: UNKNOWN_FIELD,
                "github")
                .apply {
                    this.profileName = githubUserInfo.login
                    this.profileImageUrl = githubUserInfo.avatarUrl
                }
    }

    private fun getGithubUserInfo(map: Any) = objectMapper.convertValue(map, GithubUserInfo::class.java)
}