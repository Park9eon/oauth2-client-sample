package com.park9eon.home.model

/**
 * Initial version by: park9eon
 * Initial version created on: 05/04/2018
 */
open class Profile {
    open lateinit var id: Any
    open lateinit var email: String
    open lateinit var details: String
    open lateinit var source: String
    open var name: String? = null
    open var imageUrl: String? = null
}