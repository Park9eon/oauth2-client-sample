package com.park9eon.home.model.user

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
    @JsonIgnore
    open lateinit var id: String

    @NotNull
    @JsonIgnore
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.EAGER)
    open lateinit var user: User

    @NotNull
    @JsonIgnore
    open lateinit var serviceName: String

    @NotNull
    open var name: String? = null
    open var imageUrl: String? = null

    @JsonIgnore
    @Column(columnDefinition = "TEXT")
    open var details: String? = null

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    open lateinit var createdDate: Date

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    open lateinit var modifiedDate: Date
}