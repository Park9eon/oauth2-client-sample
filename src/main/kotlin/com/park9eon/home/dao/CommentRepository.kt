package com.park9eon.home.dao

import com.park9eon.home.domain.Comment
import com.park9eon.home.support.RestrictedRepository

interface CommentRepository: RestrictedRepository<Comment, Long>