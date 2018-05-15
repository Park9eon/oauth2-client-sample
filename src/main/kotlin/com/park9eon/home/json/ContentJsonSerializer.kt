package com.park9eon.home.json

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.park9eon.home.domain.Content
import org.springframework.boot.jackson.JsonComponent


/**
 * Initial version by: park9eon
 * Initial version created on: 15/05/2018
 */
@JsonComponent
open class ContentJsonSerializer : JsonSerializer<Content>() {
    /**
     * language=JSON
     * {
     *  id : number
     *  title : string
     *  source : string
     *  tags : string[]?
     *  category : string?
     *  type : string
     *  created_date : date
     * }
     */
    override fun serialize(content: Content, gen: JsonGenerator, serializers: SerializerProvider?) {
        gen.writeStartObject()
        gen.writeNumberField("id", content.id)
        gen.writeStringField("title", content.title)
        gen.writeStringField("source", content.source)
        gen.writeArrayFieldStart("tags")
        content.tags?.forEach {
            gen.writeString(it.tag.name)
        }
        gen.writeEndArray()
        gen.writeStringField("category", content.contentCategory?.name)
        gen.writeStringField("type", content.type.name)
        gen.writeObjectField("created_date", content.createdDate)
        gen.writeEndObject()
    }
}