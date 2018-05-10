package com.park9eon.home.model

import javax.persistence.*

@Entity
@Inheritance
abstract class RestrictedEntity {
    @get:Id
    abstract var id: Long
    abstract var status: State
}