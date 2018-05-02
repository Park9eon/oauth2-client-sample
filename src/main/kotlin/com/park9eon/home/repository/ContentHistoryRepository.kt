package com.park9eon.home.repository

import com.park9eon.home.model.content.ContentHistory
import org.springframework.data.jpa.repository.JpaRepository


/**
 * Initial version by: park9eon
 * Initial version created on: 01/05/2018
 */
interface ContentHistoryRepository: JpaRepository<ContentHistory, Long>