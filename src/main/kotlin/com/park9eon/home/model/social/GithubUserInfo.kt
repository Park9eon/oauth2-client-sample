package com.park9eon.home.model.social

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty


/**
 * Initial version by: park9eon
 * Initial version created on: 05/04/2018
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class GithubUserInfo {
    var id: Long? = null
    @JsonProperty("avatar_url")
    var avatarUrl: String? = null
    var email: String? = null
    var login: String? = null
}