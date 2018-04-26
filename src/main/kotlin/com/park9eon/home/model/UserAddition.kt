package com.park9eon.home.model

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
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JsonIgnore
    open lateinit var user: User
    @NotNull
    @JsonIgnore
    open lateinit var serviceName: String
    @NotNull
    open var name: String? = null
    open var imageUrl: String? = null
    @Column(columnDefinition = "TEXT")
    @JsonIgnore
    open var details: String? = null
}