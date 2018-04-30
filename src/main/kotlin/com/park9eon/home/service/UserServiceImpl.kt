package com.park9eon.home.service

import com.park9eon.home.extenstion.create
import com.park9eon.home.extenstion.get
import com.park9eon.home.model.auth.Profile
import com.park9eon.home.model.user.Role
import com.park9eon.home.model.user.User
import com.park9eon.home.model.user.UserAddition
import com.park9eon.home.repository.UserAdditionRepository
import com.park9eon.home.repository.UserRepository
import com.park9eon.home.repository.UserRoleRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Initial version by: park9eon
 * Initial version created on: 06/04/2018
 */
@Service
open class UserServiceImpl(
        val userRepository: UserRepository,
        val userAdditionRepository: UserAdditionRepository,
        val userRoleRepository: UserRoleRepository
) : UserService {

    companion object {
        const val UNKNOWN_NAME = "아무개"
    }

    @Transactional
    override fun findOrCreateByProfile(profile: Profile): User {
        val additionId = "${profile.serviceName}:${profile.id}"
        val userAddition = try {
            userAdditionRepository.get(additionId)
        } catch (e: Exception) {
            UserAddition()
                    .apply {
                        this.id = additionId
                        this.serviceName = profile.serviceName
                        this.user = userRepository.findByUsername(profile.email
                                ?: additionId) ?: userRepository.create {
                            username = profile.email ?: additionId
                            enabled = true
                            roles = mutableSetOf(userRoleRepository.create {
                                this.user = user
                                this.role = Role.ROLE_USER
                            })
                        }

                    }
        }
        return userAddition.run {
            this.imageUrl = profile.imageUrl
            this.name = profile.name ?: UNKNOWN_NAME
            this.details = profile.details
            userAdditionRepository.save(this).user
        }
    }

    @Transactional(readOnly = true)
    override fun findById(id: Long): User {
        return userRepository.get(id)
    }
}