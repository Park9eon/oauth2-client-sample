package com.park9eon.home.repository

import com.park9eon.home.model.content.Content
import org.springframework.data.jpa.repository.JpaRepository

interface ContentRepository: JpaRepository<Content, Long>