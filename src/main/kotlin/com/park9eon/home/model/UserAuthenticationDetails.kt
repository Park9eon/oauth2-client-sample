package com.park9eon.home.model

/**
 * Initial version by: park9eon
 * Initial version created on: 05/04/2018
 */
data class UserAuthenticationDetails(val id: Any, val username: String, val type: String) {
    var profileName: String? = null
    var profileImageUrl: String? = null
}