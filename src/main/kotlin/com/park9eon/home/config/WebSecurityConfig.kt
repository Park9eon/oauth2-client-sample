package com.park9eon.home.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.NestedConfigurationProperty
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.client.OAuth2ClientContext
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.web.filter.CompositeFilter
import javax.servlet.Filter

/**
 * Initial version by: park9eon
 * Initial version created on: 04/04/2018
 */
@Configuration
@EnableOAuth2Client
open class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    lateinit var oauth2ClientContext: OAuth2ClientContext

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.antMatcher("/**").authorizeRequests()
                .antMatchers("/", "/oauth/callback").permitAll()
                .anyRequest().authenticated()
                .and().exceptionHandling().authenticationEntryPoint(LoginUrlAuthenticationEntryPoint("/"))
                .and().logout().logoutRequestMatcher(AntPathRequestMatcher("/logout")).logoutSuccessUrl("/").permitAll()
                .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and().addFilterBefore(ssoFilter(), BasicAuthenticationFilter::class.java)
    }

    @Bean
    open fun oauth2ClientFilterRegistration(filter: OAuth2ClientContextFilter): FilterRegistrationBean<*> {
        val registration = FilterRegistrationBean(filter)
        registration.order = -100
        return registration
    }

    @Bean
    @ConfigurationProperties("facebook")
    open fun facebook() = ClientResources()

    @Bean
    @ConfigurationProperties("github")
    open fun github() = ClientResources()

    @Bean
    @ConfigurationProperties("google")
    open fun google() = ClientResources()

    @Bean
    @ConfigurationProperties("kakao")
    open fun kakao() = ClientResources()

    @Bean
    @ConfigurationProperties("naver")
    open fun naver() = ClientResources()

    private fun ssoFilter(): Filter = CompositeFilter()
            .apply {
                setFilters(listOf(
                        createFilter(facebook(), "/auth/facebook"),
                        createFilter(github(), "/auth/github"),
                        createFilter(google(), "/auth/google"),
                        createFilter(kakao(), "/auth/kakao"),
                        createFilter(naver(), "/auth/naver")
                ))
            }

    private fun createFilter(clientResources: ClientResources, processUrl: String): Filter {
        val filter = OAuth2ClientAuthenticationProcessingFilter(processUrl)
        val template = OAuth2RestTemplate(clientResources.client, oauth2ClientContext)
        filter.setRestTemplate(template)
        val tokenServices = UserInfoTokenServices(clientResources.resource.userInfoUri, clientResources.client.clientId)
        tokenServices.setRestTemplate(template)
        filter.setTokenServices(tokenServices)
        return filter
    }
}

class ClientResources {
    @NestedConfigurationProperty
    val client = AuthorizationCodeResourceDetails()

    @NestedConfigurationProperty
    val resource = ResourceServerProperties()
}