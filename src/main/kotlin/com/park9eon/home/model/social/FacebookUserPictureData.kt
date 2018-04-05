package com.park9eon.home.model.social

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class FacebookUserPictureData {
    var width: Int? = null
    var height: Int? = null
    var url: String? = null
}
