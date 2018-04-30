package com.park9eon.home.repository

import com.park9eon.home.model.user.UserRole
import org.springframework.data.jpa.repository.JpaRepository

interface UserRoleRepository: JpaRepository<UserRole, Long>