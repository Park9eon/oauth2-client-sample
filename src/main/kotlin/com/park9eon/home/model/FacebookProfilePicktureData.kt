package com.park9eon.home.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class FacebookProfilePicktureData {
    var width: Int? = null
    var height: Int? = null
    var url: String? = null
}
