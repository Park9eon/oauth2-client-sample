package com.park9eon.home.model.content

import javax.persistence.*
import javax.validation.constraints.NotNull


/**
 * Initial version by: park9eon
 * Initial version created on: 28/04/2018
 */
@Entity
open class ContentAddition {
    @Id
    open var id: Long = 0
    @NotNull
    @JoinColumn(name = "content_id")
    @ManyToOne(fetch = FetchType.LAZY)
    open lateinit var content: Content
}