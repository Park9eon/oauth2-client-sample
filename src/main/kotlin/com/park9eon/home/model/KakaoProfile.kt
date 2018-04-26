package com.park9eon.home.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty


/**
 * Initial version by: park9eon
 * Initial version created on: 05/04/2018
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class KakaoProfile : Profile() {
    @JsonProperty("kaccount_email")
    override var email: String? = null
    var properties: KakaoProfileProperties? = null
    override var name: String?
        get() = this.properties?.nickname
        set(value) {
            this.properties?.nickname = value
        }
}