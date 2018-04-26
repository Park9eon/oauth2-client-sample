package com.park9eon.home.service

import com.park9eon.home.model.Profile
import com.park9eon.home.model.User
import com.park9eon.home.model.UserAddition
import com.park9eon.home.model.UserRole
import com.park9eon.home.repository.UserAdditionRepository
import com.park9eon.home.repository.UserRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional


/**
 * Initial version by: park9eon
 * Initial version created on: 06/04/2018
 */
@Service("userService")
@Transactional
open class UserService(
        val userRepository: UserRepository,
        val userAdditionRepository: UserAdditionRepository
) {

    companion object {
        const val UNKNOWN_NAME = "아무개"
    }

    @Transactional
    open fun findOrCreateByProfile(profile: Profile): User {
        val additionId = "${profile.serviceName}:${profile.id}"
        val userAddition = if (userAdditionRepository.existsById(additionId)) {
            userAdditionRepository.getOne(additionId)
        } else {
            UserAddition()
                    .apply {
                        this.id = additionId
                        this.serviceName = profile.serviceName
                        this.user = userRepository.findByUsername(profile.email ?: additionId) ?: User()
                                .apply {
                                    username = profile.email ?: additionId
                                    enabled = true
                                    roles = mutableSetOf(UserRole(this, UserRole.ROLE_USER))
                                    userRepository.save(this)
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
}