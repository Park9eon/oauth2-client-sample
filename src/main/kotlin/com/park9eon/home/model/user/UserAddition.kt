package com.park9eon.home.model.user

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*
import javax.validation.constraints.NotNull


/**
 * Initial version by: park9eon
 * Initial version created on: 08/04/2018
 *
 * 유저와 프로파일을 연결하는 부분
 */
@Entity
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
}