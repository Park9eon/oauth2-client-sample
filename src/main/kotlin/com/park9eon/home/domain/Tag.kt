package com.park9eon.home.domain

import com.park9eon.home.domain.ContentTag
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
open class Tag {

    constructor()

    constructor(name: String) : this() {
        this.name = name
    }

    @Id
    @Column(nullable = false, unique = true, updatable = false)
    open lateinit var name: String

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tag")
    open var contentTags: MutableSet<ContentTag>? = null

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    open var createdDate: Date? = null

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    open var lastModifiedDate: Date? = null
}