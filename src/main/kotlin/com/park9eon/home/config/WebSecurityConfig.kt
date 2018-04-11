package com.park9eon.home.config

import com.park9eon.home.converter.DefaultTokenProfileConverter
import com.park9eon.home.converter.NaverResultTokenProfileConverter
import com.park9eon.home.converter.TokenProfileConverter
import com.park9eon.home.filter.OAuth2ProfileAuthenticationProcessingFilter
import com.park9eon.home.model.*
import com.park9eon.home.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.client.OAuth2ClientContext
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession
import org.springframework.web.filter.CompositeFilter
import javax.servlet.Filter
import kotlin.reflect.KClass

/**
 * Initial version by: park9eon
 * Initial version created on: 04/04/2018
 */
@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
@EnableRedisHttpSession
open class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    lateinit var oauth2ClientContext: OAuth2ClientContext
    @Autowired
    lateinit var userService: UserService

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.antMatcher("/**").authorizeRequests()
                .antMatchers("/", "/auth/**").permitAll()
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

    fun facebookOAuth2ClientProcessingFilter() = oauth2ProfileProcessingFilter(
            facebook(), FacebookProfile::class, "/auth/facebook")

    @Bean
    @ConfigurationProperties("github")
    open fun github() = ClientResources()

    fun githubOAuth2ClientProcessingFilter() = oauth2ProfileProcessingFilter(
            github(), GithubProfile::class, "/auth/github")

    @Bean
    @ConfigurationProperties("google")
    open fun google() = ClientResources()

    fun googleOAuth2ClientProcessingFilter() = oauth2ProfileProcessingFilter(
            google(), GoogleProfile::class, "/auth/google")

    @Bean
    @ConfigurationProperties("kakao")
    open fun kakao() = ClientResources()

    fun kakaoOAuth2ClientProcessingFilter() = oauth2ProfileProcessingFilter(
            kakao(), KakaoProfile::class, "/auth/kakao")

    @Bean
    @ConfigurationProperties("naver")
    open fun naver() = ClientResources()

    fun naverOAuth2ClientProcessingFilter() = oauth2ProfileProcessingFilter(
            naver(), NaverResultTokenProfileConverter(), "/auth/naver")

    private fun ssoFilter(): Filter = CompositeFilter()
            .apply {
                setFilters(listOf(
                        facebookOAuth2ClientProcessingFilter(),
                        githubOAuth2ClientProcessingFilter(),
                        googleOAuth2ClientProcessingFilter(),
                        kakaoOAuth2ClientProcessingFilter(),
                        naverOAuth2ClientProcessingFilter()
                ))
            }

    private fun oauth2ProfileProcessingFilter(
            clientResources: ClientResources,
            profileConverter: TokenProfileConverter<*>,
            processingUrl: String
    ) = OAuth2ProfileAuthenticationProcessingFilter(userService, profileConverter, processingUrl)
            .apply {
                val template = OAuth2RestTemplate(clientResources.client, oauth2ClientContext)
                this.setRestTemplate(template)
                val userInfoTokenServices = UserInfoTokenServices(clientResources.resource.userInfoUri, clientResources.client.clientId)
                userInfoTokenServices.setRestTemplate(template)
                this.setTokenServices(userInfoTokenServices)
            }

    private fun <T : Profile> oauth2ProfileProcessingFilter(
            clientResources: ClientResources,
            profileClazz: KClass<T>,
            processingUrl: String
    ) = this.oauth2ProfileProcessingFilter(clientResources, DefaultTokenProfileConverter(profileClazz), processingUrl)

    @Bean
    open fun tokenStore(): TokenStore {
        return RedisTokenStore(connectionFactory())
    }

    @Bean
    open fun connectionFactory(): LettuceConnectionFactory {
        return LettuceConnectionFactory()
    }
}