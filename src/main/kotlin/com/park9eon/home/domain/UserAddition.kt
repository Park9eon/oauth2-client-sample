package com.park9eon.home.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull


/**
 * Initial version by: park9eon
 * Initial version created on: 08/04/2018
 *
 * 유저와 프로파일을 연결하는 부분
 */
@Entity
@EntityListeners(AuditingEntityListener::class)
open class UserAddition {
    // 'source-id' 형식
    @Id
    open lateinit var id: String

    @JoinColumn(nullable = false, name = "user_id", updatable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    open lateinit var user: User

    @Column(nullable = false)
    open lateinit var serviceName: String

    open var name: String? = null

    open var imageUrl: String? = null

    @Column(columnDefinition = "TEXT")
    open var details: String? = null

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    open lateinit var createdDate: Date

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    open lateinit var modifiedDate: Date
}