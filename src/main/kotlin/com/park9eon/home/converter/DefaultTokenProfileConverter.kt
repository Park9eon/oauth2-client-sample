package com.park9eon.home.converter

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.park9eon.home.model.auth.Profile
import kotlin.reflect.KClass

/**
 * Initial version by: park9eon
 * Initial version created on: 11/04/2018
 */
class DefaultTokenProfileConverter<T : Profile>(var clazz: KClass<T>, override var objectMapper: ObjectMapper = jacksonObjectMapper()) : TokenProfileConverter<T> {
    override fun convert(details: Any): T {
        return objectMapper.readValue(objectMapper.writeValueAsString(details), clazz.java)
    }
}