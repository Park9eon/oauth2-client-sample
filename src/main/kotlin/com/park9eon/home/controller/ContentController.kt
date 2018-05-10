package com.park9eon.home.controller

import com.park9eon.home.service.ContentService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
open class ContentController(
        val contentService: ContentService
) {

    @GetMapping("/")
    fun index(@RequestParam(name = "p", required = false) page: Int = 1, model: Model): String {
        model.addAttribute("contents", this.contentService.getAll(page))
        model.addAttribute("page", page)
        return "content/index"
    }

    @GetMapping("/{id}")
    fun show(@PathVariable(name = "id") id: Long, model: Model): String {
        model.addAttribute("content", this.contentService.get(id))
        return "content/show"
    }

    @GetMapping("/editor")
    fun editor(): String {
        return "content/editor"
    }

    @PostMapping("/save")
    fun save(): String {
        return "redirect:/"
    }
}