package com.park9eon.home.annotation

/**
 * Initial version by: park9eon
 * Initial version created on: 10/04/2018
 *
 * Controller 에서 세션기반으로 User를 대입
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Me(
        // Username 기반으로 최신값을 가져오는 여부
        val load: Boolean = false
)