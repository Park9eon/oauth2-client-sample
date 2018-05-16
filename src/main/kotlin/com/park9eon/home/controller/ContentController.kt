package com.park9eon.home.controller

import com.park9eon.home.annotation.Me
import com.park9eon.home.domain.Comment
import com.park9eon.home.domain.Content
import com.park9eon.home.domain.User
import com.park9eon.home.service.ContentService
import org.springframework.data.domain.Page
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/content")
open class ContentController(
        val contentService: ContentService
) {

    @GetMapping("/")
    fun getContents(@RequestParam(name = "category", required = false) categoryId: Long? = null,
                    @RequestParam(name = "page", required = false, defaultValue = "0") offset: Int) =
            this.contentService.getContents(categoryId = categoryId, offset = offset)

    @GetMapping("/{contentId}")
    fun getContent(@PathVariable(name = "contentId") contentId: Long) =
            this.contentService.getContent(contentId)

    @Secured("ROLE_USER")
    @PostMapping("/")
    fun saveContent(@RequestBody content: Content) =
            this.contentService.saveContent(content)

    @Secured("ROLE_USER")
    @PutMapping("/")
    fun updateContent(@RequestBody content: Content) =
            this.contentService.updateContent(content)

    @Secured("ROLE_USER")
    @DeleteMapping("/{contentId}")
    fun deleteContent(@PathVariable(name = "contentId") contentId: Long) =
            this.contentService.deleteContent(contentId)

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{contentId}")
    fun forceDeleteContent(@PathVariable(name = "contentId") contentId: Long,
                           @RequestParam(name = "option") option: String) =
            this.contentService.deleteContent(contentId, option == "force")

    @Secured("ROLE_USER")
    @PutMapping("/{contentId}/tag/{tagName}")
    fun addContentTag(@PathVariable(name = "contentId") contentId: Long,
                      @PathVariable(name = "tagName") tagName: String) =
            this.contentService.addContentTag(contentId, tagName)

    @Secured("ROLE_USER")
    @DeleteMapping("/{contentId}/tag/{tagName}")
    fun deleteContentTag(@PathVariable(name = "contentId") contentId: Long,
                         @PathVariable(name = "tagName") tagName: String) =
            this.contentService.deleteContentTag(contentId, tagName)

    @GetMapping("/{contentId}/comment/{commentId}")
    fun getComments(@PathVariable(name = "contentId") contentId: Long,
                    @PathVariable(name = "commentId", required = false) commentId: Long?,
                    @RequestParam(name = "page", required = false, defaultValue = "0") offset: Int): Page<Comment> {
        return this.contentService.getComments(contentId, commentId, offset)
    }

    @Secured("ROLE_USER")
    @PostMapping("/{contentId}/comment/{commentId}")
    fun saveComment(@PathVariable(name = "contentId") contentId: Long,
                    @PathVariable(name = "commentId", required = false) commentId: Long?,
                    @RequestBody comment: Comment): Comment {
        comment.parent = commentId?.let { Comment(it) }
        return this.contentService.saveComment(contentId, comment)
    }

    @Secured("ROLE_USER")
    @PutMapping("/{contentId}/comment")
    fun updateComment(@PathVariable(name = "contentId") contentId: Long,
                      @RequestBody comment: Comment): Comment {
        return this.contentService.updateComment(comment)
    }

    @Secured("ROLE_USER")
    @DeleteMapping("/{contentId}/comment/{commentId}")
    fun deleteComment(@PathVariable(name = "contentId") contentId: Long,
                      @PathVariable(name = "commentId") commentId: Long) {
        this.contentService.deleteComment(commentId)
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{contentId}/comment/{commentId}")
    fun forceDeleteComment(@PathVariable(name = "contentId") contentId: Long,
                           @PathVariable(name = "commentId") commentId: Long,
                           @RequestParam(name = "option") option: String) =
            this.contentService.deleteContent(commentId, option == "force")
}