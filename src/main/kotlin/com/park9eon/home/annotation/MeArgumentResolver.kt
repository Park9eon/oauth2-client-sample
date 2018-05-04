package com.park9eon.home.annotation

import com.park9eon.home.domain.User
import com.park9eon.home.model.UserDetails
import com.park9eon.home.service.UserService
import org.springframework.core.MethodParameter
import org.springframework.core.annotation.AnnotationUtils
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer


/**
 * Initial version by: park9eon
 * Initial version created on: 28/04/2018
 */
open class MeArgumentResolver(
        private var userService: UserService
) : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return this.findMethodAnnotation<Me>(parameter) != null
    }

    override fun resolveArgument(parameter: MethodParameter, mavContainer: ModelAndViewContainer?, webRequest: NativeWebRequest, binderFactory: WebDataBinderFactory?): Any? {
        val userDetails = SecurityContextHolder.getContext().authentication as? UserDetails ?: return null
        val user = userDetails.let {
            val id = it.id
            val username = it.name
            if (id != null && username != null) {
                User().apply {
                    this.id = id
                    this.username = username
                }
            } else {
                null
            }
        }
        val meAnnotation = this.findMethodAnnotation<Me>(parameter)
        return if (user != null && meAnnotation?.load == true) {
            userService.findById(user.id)
        } else {
            user
        }
    }

    private inline fun <reified T : Annotation> findMethodAnnotation(parameter: MethodParameter): T? {
        var annotation: T? = parameter.getParameterAnnotation(T::class.java)
        if (annotation != null) {
            return annotation
        }
        val annotationsToSearch = parameter.parameterAnnotations
        for (toSearch in annotationsToSearch) {
            annotation = AnnotationUtils.findAnnotation(toSearch.javaClass, T::class.java)
            if (annotation != null) {
                return annotation
            }
        }
        return null
    }

}