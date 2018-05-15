package com.park9eon.home.json

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.park9eon.home.domain.User
import org.springframework.boot.jackson.JsonComponent


/**
 * Initial version by: park9eon
 * Initial version created on: 15/05/2018
 */
@JsonComponent
open class UserJsonSerializer : JsonSerializer<User>() {
    /**
     * language=JSON
     * {
     *  id : number
     *  username : string
     *  profile_name : string?
     *  profile_picture : string?
     * }
     */
    override fun serialize(user: User, gen: JsonGenerator, serializers: SerializerProvider?) {
        gen.writeStartObject()
        gen.writeNumberField("id", user.id)
        gen.writeStringField("username", user.username)
        gen.writeStringField("profile_name", user.additions?.first()?.name)
        gen.writeStringField("profile_picture", user.additions?.first()?.imageUrl)
        gen.writeEndObject()
    }
}