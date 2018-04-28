package com.park9eon.home.extenstion

import org.springframework.data.jpa.repository.JpaRepository


/**
 * Initial version by: park9eon
 * Initial version created on: 28/04/2018
 */
fun <T, R> JpaRepository<T, R>.get(id: R?): T? {
    return id?.let {
        this.findById(id).orElse(null)
    }
}