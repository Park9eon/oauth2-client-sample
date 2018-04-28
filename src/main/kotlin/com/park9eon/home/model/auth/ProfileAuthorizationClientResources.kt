package com.park9eon.home.model.auth

import org.springframework.boot.context.properties.NestedConfigurationProperty

open class ProfileAuthorizationClientResources {

    @NestedConfigurationProperty
    val client = ProfileAuthorizationClientDetails()

    @NestedConfigurationProperty
    val resource = ProfileAuthorizationResourceDetails()
}