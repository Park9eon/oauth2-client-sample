package com.park9eon.home.controller

import com.park9eon.home.annotation.Me
import com.park9eon.home.command.ContentCommand
import com.park9eon.home.domain.User
import com.park9eon.home.service.ContentService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/content")
open class ContentController(
        val contentService: ContentService
) {

    @GetMapping
    fun index(@RequestParam(name = "offset", required = false, defaultValue = "0") page: Int) =
            this.contentService.getAll(page)

    @GetMapping("/{id}")
    fun show(@PathVariable(name = "id") id: Long) =
            this.contentService.getOne(id)

    @PostMapping
    fun save(@Me me: User, @RequestBody contentCommand: ContentCommand) =
            this.contentService.save(me.id, contentCommand.source, contentCommand.type)

    @PutMapping("/{id}")
    fun update(@Me me: User, @PathVariable(name = "id") id: Long, @RequestBody contentCommand: ContentCommand) =
            this.contentService.update(me.id, id, contentCommand.source)

    @DeleteMapping("/{id}")
    fun delete(@Me me: User, @PathVariable(name = "id") id: Long) =
            this.contentService.delete(me.id, id)
}