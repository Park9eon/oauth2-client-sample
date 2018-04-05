package com.park9eon.home.model.social

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class NaverUserResponse {
    var id: Long? = null
    var email: String? = null
    var name: String? = null
    var nickname: String? = null
    @JsonProperty("profile_image")
    var profileImage: String? = null
}
