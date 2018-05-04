package com.park9eon.home.support

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.park9eon.home.model.Profile
import com.park9eon.home.model.ProfileAuthorizationClientResources
import com.park9eon.home.model.UserDetails
import com.park9eon.home.service.UserService
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter
import org.springframework.security.oauth2.provider.OAuth2Authentication
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Initial version by: park9eon
 * Initial version created on: 11/04/2018
 */
open class OAuth2ProfileAuthenticationProcessingFilter(
        private val userService: UserService,
        private val clientResources: ProfileAuthorizationClientResources,
        private val tokenProfileConverter: TokenProfileConverter<*>
) : OAuth2ClientAuthenticationProcessingFilter(clientResources.client.processesUrl) {

    open var objectMapper = jacksonObjectMapper()

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication? {
        // access토큰이 있을경우 무조건 삭제해줌
        this.restTemplate.oAuth2ClientContext?.accessToken = null
        return (super.attemptAuthentication(request, response) as? OAuth2Authentication)
                ?.userAuthentication?.let {
            val profile: Profile = this.tokenProfileConverter.convert(it.details)
            profile.details = objectMapper.writeValueAsString(it.details)
            profile.serviceName = this.clientResources.client.clientName
            val user = userService.findOrCreateByProfile(profile)
            UserDetails(user)
        }
    }
}