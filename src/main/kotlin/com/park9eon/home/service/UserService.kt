package com.park9eon.home.service

import com.park9eon.home.model.auth.Profile
import com.park9eon.home.model.user.User

interface UserService {
    fun findById(id: Long): User
    fun findOrCreateByProfile(profile: Profile): User
    fun currentUser(): User?
}