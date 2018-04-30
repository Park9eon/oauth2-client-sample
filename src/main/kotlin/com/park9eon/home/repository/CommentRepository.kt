package com.park9eon.home.repository

import com.park9eon.home.model.content.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository: JpaRepository<Comment, Long>