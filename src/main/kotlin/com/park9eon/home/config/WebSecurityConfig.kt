package com.park9eon.home.config

import com.park9eon.home.model.ClientResources
import com.park9eon.home.service.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.client.OAuth2ClientContext
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.web.filter.CompositeFilter
import javax.servlet.Filter
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.userdetails.UserDetailsService

/**
 * Initial version by: park9eon
 * Initial version created on: 04/04/2018
 */
@Configuration
@EnableOAuth2Client
open class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    lateinit var oauth2ClientContext: OAuth2ClientContext
    @Autowired
    lateinit var userDetailsService: JpaUserDetailsService

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService<UserDetailsService>(userDetailsService)
    }

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

    fun facebookOAuth2ClientProcessingFilter() = OAuth2ClientAuthenticationProcessingFilter("/auth/facebook")
            .apply {
                facebook()
                        .let { facebook ->
                            val template = OAuth2RestTemplate(facebook.client, oauth2ClientContext)
                            val facebookUserInfoTokenService = FacebookUserInfoTokenService(facebook, template)
                            this.setRestTemplate(template)
                            this.setTokenServices(facebookUserInfoTokenService)
                        }
            }

    @Bean
    @ConfigurationProperties("github")
    open fun github() = ClientResources()

    fun githubOAuth2ClientProcessingFilter() = OAuth2ClientAuthenticationProcessingFilter("/auth/github")
            .apply {
                github()
                        .let { github ->
                            val template = OAuth2RestTemplate(github.client, oauth2ClientContext)
                            val githubUserInfoTokenService = GithubUserInfoTokenService(github, template)
                            this.setRestTemplate(template)
                            this.setTokenServices(githubUserInfoTokenService)
                        }
            }

    @Bean
    @ConfigurationProperties("google")
    open fun google() = ClientResources()

    fun googleOAuth2ClientProcessingFilter() = OAuth2ClientAuthenticationProcessingFilter("/auth/google")
            .apply {
                google()
                        .let { google ->
                            val template = OAuth2RestTemplate(google.client, oauth2ClientContext)
                            val googleUserInfoTokenService = GoogleUserInfoTokenService(google, template)
                            this.setRestTemplate(template)
                            this.setTokenServices(googleUserInfoTokenService)
                        }
            }

    @Bean
    @ConfigurationProperties("kakao")
    open fun kakao() = ClientResources()

    fun kakaoOAuth2ClientProcessingFilter() = OAuth2ClientAuthenticationProcessingFilter("/auth/kakao")
            .apply {
                kakao()
                        .let { kakao ->
                            val template = OAuth2RestTemplate(kakao.client, oauth2ClientContext)
                            val kakaoUserInfoTokenService = KakaoUserInfoTokenService(kakao, template)
                            this.setRestTemplate(template)
                            this.setTokenServices(kakaoUserInfoTokenService)
                        }
            }

    @Bean
    @ConfigurationProperties("naver")
    open fun naver() = ClientResources()

    fun naverOAuth2ClientProcessingFilter() = OAuth2ClientAuthenticationProcessingFilter("/auth/naver")
            .apply {
                naver()
                        .let { naver ->
                            val template = OAuth2RestTemplate(naver.client, oauth2ClientContext)
                            val naverUserInfoTokenService = NaverUserInfoTokenService(naver, template)
                            this.setRestTemplate(template)
                            this.setTokenServices(naverUserInfoTokenService)
                        }
            }

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
}