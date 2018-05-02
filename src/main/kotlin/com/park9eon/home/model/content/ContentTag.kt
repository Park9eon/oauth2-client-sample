package com.park9eon.home.model.content

import javax.persistence.*
import javax.validation.constraints.NotNull


/**
 * Initial version by: park9eon
 * Initial version created on: 30/04/2018
 */
@Entity
open class ContentTag(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0
) {

    @JoinColumn(name = "content_id")
    @ManyToOne(fetch = FetchType.EAGER)
    open lateinit var content: Content

    @NotNull
    lateinit var tag: String
}