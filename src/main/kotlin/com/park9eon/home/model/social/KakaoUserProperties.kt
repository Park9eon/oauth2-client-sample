package com.park9eon.home.model.social

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty


/**
 * Initial version by: park9eon
 * Initial version created on: 05/04/2018
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class KakaoUserProperties {
    @JsonProperty("profile_image")
    var profileImage: String? = null
    var nickname: String? = null
}