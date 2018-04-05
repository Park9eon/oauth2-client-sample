package com.park9eon.home.model.social

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * Initial version by: park9eon
 * Initial version created on: 05/04/2018
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class GoogleUserInfo {
    var sub: Long? = null
    var name: String? = null
    var picture: String? = null
    var email: String? = null
    var profile: String? = null
}