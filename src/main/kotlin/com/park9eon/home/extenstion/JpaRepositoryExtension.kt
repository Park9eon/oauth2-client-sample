package com.park9eon.home.extenstion

import org.springframework.data.jpa.repository.JpaRepository
import kotlin.reflect.full.createInstance


/**
 * Initial version by: park9eon
 * Initial version created on: 28/04/2018
 */
fun <T, R> JpaRepository<T, R>.get(id: R): T {
    return this.findById(id).get()
}

fun <T, R> JpaRepository<T, R>.save(entity: T, block: T.() -> Unit): T {
    return this.save(entity.apply(block))
}

inline fun <reified T : Any, R> JpaRepository<T, R>.create(block: T.() -> Unit): T {
    return this.save(T::class.createInstance().apply(block))
}
