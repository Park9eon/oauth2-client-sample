package com.park9eon.home.command

import com.park9eon.home.model.ContentType

class ContentCommand(
        var id: Long = 0
) {
    lateinit var title: String
    lateinit var source: String
    lateinit var type: ContentType
    var tags: List<String?>? = null
}