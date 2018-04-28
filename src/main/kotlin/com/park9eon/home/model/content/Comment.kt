package com.park9eon.home.model.content

import com.park9eon.home.model.user.User
import javax.persistence.*

/**
 * Initial version by: park9eon
 * Initial version created on: 28/04/2018
 */
@Entity
open class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long = 0
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.EAGER)
    open lateinit var user: User
    @JoinColumn(name = "content_id")
    @ManyToOne(fetch = FetchType.EAGER)
    open lateinit var content: Content
    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], mappedBy = "comment")
    open lateinit var additions: MutableSet<CommentAddition>
}