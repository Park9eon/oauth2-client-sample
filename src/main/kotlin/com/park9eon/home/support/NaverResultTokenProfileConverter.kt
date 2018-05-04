package com.park9eon.home.support

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.park9eon.home.model.NaverProfile
import com.park9eon.home.model.NaverProfileWrapper


/**
 * Initial version by: park9eon
 * Initial version created on: 11/04/2018
 */
class NaverResultTokenProfileConverter(override var objectMapper: ObjectMapper = jacksonObjectMapper()) : TokenProfileConverter<NaverProfile> {
    override fun convert(details: Any): NaverProfile {
        return objectMapper.readValue(objectMapper.writeValueAsString(details), NaverProfileWrapper::class.java)?.profile!!
    }
}