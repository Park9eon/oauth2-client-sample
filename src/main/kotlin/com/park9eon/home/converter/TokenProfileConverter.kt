package com.park9eon.home.converter

import com.fasterxml.jackson.databind.ObjectMapper
import com.park9eon.home.model.Profile

/**
 * Initial version by: park9eon
 * Initial version created on: 11/04/2018
 */
interface TokenProfileConverter<out T : Profile> {
    var objectMapper: ObjectMapper
    fun convert(details: Any): T
}