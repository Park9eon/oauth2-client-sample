package com.park9eon.home.model.social

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty


/**
 * Initial version by: park9eon
 * Initial version created on: 05/04/2018
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class KakaoUserInfo {
    var id: Long? = null
    @JsonProperty("kaccount_email")
    var kaccountEmail: String? = null
    var properties: KakaoUserProperties? = null
}