package com.park9eon.home.support

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean
import kotlin.reflect.full.createInstance

@NoRepositoryBean
interface KpaRepository<T, R> : JpaRepository<T, R> {
    @JvmDefault
    fun save(entity: T, block: T.() -> Unit): T = this.save(entity.apply(block))
}

inline fun <reified T : Any, R> KpaRepository<T, R>.create(block: T.() -> Unit): T {
    return this.save(T::class.createInstance().apply(block))
}
