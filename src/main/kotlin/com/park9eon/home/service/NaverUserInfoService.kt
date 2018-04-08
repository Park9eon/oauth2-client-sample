package com.park9eon.home.service

import com.park9eon.home.model.ClientResources
import com.park9eon.home.model.NaverProfileWrapper
import org.springframework.security.oauth2.client.OAuth2RestTemplate


/**
 * Initial version by: park9eon
 * Initial version created on: 05/04/2018
 */
class NaverUserInfoService(userService: UserService, clientResources: ClientResources, oauth2RestTemplate: OAuth2RestTemplate)
    : ProfileUserInfoService(userService, clientResources, oauth2RestTemplate) {
    override fun convertProfile(details: Any) = detailsMapped(details, NaverProfileWrapper::class).profile!!
}