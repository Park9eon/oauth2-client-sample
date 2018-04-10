package com.park9eon.home.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.park9eon.home.model.ClientResources
import com.park9eon.home.model.Profile
import com.park9eon.home.model.UserDetails
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices
import org.springframework.security.core.AuthenticationException
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.security.oauth2.provider.OAuth2Authentication
import kotlin.reflect.KClass


/**
 * Initial version by: park9eon
 * Initial version created on: 05/04/2018
 */
abstract class ProfileUserInfoService(private val userService: UserService, clientResources: ClientResources, oauth2RestTemplate: OAuth2RestTemplate)
    : UserInfoTokenServices(clientResources.resource.userInfoUri, clientResources.client.clientId) {
    init {
        this.setRestTemplate(oauth2RestTemplate)
    }

    companion object {
        const val UNKNOWN_FIELD = "unknown"
    }

    private val objectMapper = jacksonObjectMapper()

    abstract fun convertProfile(details: Any): Profile

    fun <T : Any> detailsMapped(details: Any, clazz: KClass<T>): T {
        return objectMapper.readValue(objectMapper.writeValueAsString(details), clazz.java)
    }

    override fun getPrincipal(map: MutableMap<String, Any>) = this.convertProfile(map).email

    override fun loadAuthentication(accessToken: String?): OAuth2Authentication {
        return super.loadAuthentication(accessToken).let {
            val details = it.userAuthentication.details
            val profile = convertProfile(details)
            profile.details = objectMapper.writeValueAsString(details)
            profile.source = profile::class.simpleName ?: UNKNOWN_FIELD
            try {
                val user = userService.findOrCreateByProfile(profile)
                val userAuthentication = UserDetails(user, details)
                OAuth2Authentication(it.oAuth2Request, userAuthentication)
            } catch (e: Exception) {
                throw object : AuthenticationException("User does not find or create", e) {}
            }
        }
    }
}