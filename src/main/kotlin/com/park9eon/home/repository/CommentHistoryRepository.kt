package com.park9eon.home.repository

import com.park9eon.home.model.content.CommentHistory
import org.springframework.data.jpa.repository.JpaRepository


/**
 * Initial version by: park9eon
 * Initial version created on: 01/05/2018
 */
interface CommentHistoryRepository: JpaRepository<CommentHistory, Long>