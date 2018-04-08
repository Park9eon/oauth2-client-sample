package com.park9eon.home.model

/**
 * Initial version by: park9eon
 * Initial version created on: 05/04/2018
 */
open class Profile {
    open var id: Any? = null
    open var email: String? = null
    open var name: String? = null
    open var imageUrl: String? = null
    open var details: String? = null
    lateinit var source: String
}