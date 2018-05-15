package com.park9eon.home.dao

import com.park9eon.home.domain.Content
import com.park9eon.home.domain.ContentTag
import com.park9eon.home.domain.Tag
import com.park9eon.home.support.KpaRepository

interface ContentTagRepository : KpaRepository<ContentTag, Long> {
    fun findByContentAndTag(content: Content, Tag: Tag): ContentTag?
    fun deleteByContentAndTag_Name(content: Content, tagName: String)
}