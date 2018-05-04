package com.park9eon.home.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * Initial version by: park9eon
 * Initial version created on: 05/04/2018
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class FacebookProfilePictureWrapper {
    var data: FacebookProfilePicktureData? = null
}