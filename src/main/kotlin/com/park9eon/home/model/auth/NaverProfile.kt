package com.park9eon.home.model.auth

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class NaverProfile : Profile() {
    @JsonProperty("profile_image")
    override var imageUrl: String? = null
}
