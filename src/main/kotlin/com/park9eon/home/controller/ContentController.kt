package com.park9eon.home.controller

import com.park9eon.home.annotation.Me
import com.park9eon.home.domain.Content
import com.park9eon.home.domain.User
import com.park9eon.home.service.ContentService
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/content")
open class ContentController(
        val contentService: ContentService
) {

    @GetMapping
    fun index(@Me me: User? = null,
              @RequestParam(name = "c", required = false) categoryId: Long? = null,
              @RequestParam(name = "p", required = false, defaultValue = "0") offset: Int) =
            this.contentService.getContents(categoryId = categoryId, offset = offset)

    @GetMapping("/{id}")
    fun show(@PathVariable(name = "id") id: Long) =
            this.contentService.getContent(id)

    @PostMapping
    @Secured("ROLE_USER")
    fun save(@Me me: User, @RequestBody content: Content) =
            this.contentService.saveContent(content)

    @PutMapping("/")
    @Secured("ROLE_USER")
    fun update(@Me me: User, @PathVariable(name = "id") id: Long, @RequestBody content: Content) =
            this.contentService.updateContent(content)

    @DeleteMapping("/{id}")
    @Secured("ROLE_USER")
    fun delete(@Me me: User, @PathVariable(name = "id") id: Long) =
            this.contentService.deleteContent(me.id)

    @PutMapping("/{id}/tag/{tagName}")
    @Secured("ROLE_USER")
    fun addContentTag(@Me me: User, @PathVariable(name = "id") id: Long, @PathVariable(name = "tagName") tagName: String) =
            this.contentService.addContentTag(id, tagName)

    @DeleteMapping("/{id}/tag/{tagName}")
    @Secured("ROLE_USER")
    fun deleteContentTag(@Me me: User, @PathVariable(name = "id") id: Long, @PathVariable(name = "tagName") tagName: String) =
            this.contentService.deleteContentTag(id, tagName)
}