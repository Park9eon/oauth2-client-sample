package com.park9eon.home.dao

import com.park9eon.home.domain.Content
import com.park9eon.home.support.RestrictedRepository

interface ContentRepository: RestrictedRepository<Content, Long>