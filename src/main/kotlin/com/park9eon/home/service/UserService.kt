package com.park9eon.home.service

import com.park9eon.home.model.Profile
import com.park9eon.home.model.User
import com.park9eon.home.model.UserAddition
import com.park9eon.home.model.UserRole
import com.park9eon.home.repository.UserAdditionRepository
import com.park9eon.home.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional


/**
 * Initial version by: park9eon
 * Initial version created on: 06/04/2018
 */
@Service("userService")
@Transactional
open class UserService {

    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var userAdditionRepository: UserAdditionRepository

    open fun findOrCreateByProfile(profile: Profile): User {
        return profile.email.let { email ->
            if (email == null) {
                throw NullPointerException("Email is not null!")
            } else {
                val user = userRepository.findByUsername(email) ?: User()
                        .apply {
                            username = email
                            enabled = true
                            roles = setOf(UserRole(this, UserRole.ROLE_USER))
                        }
                userAdditionRepository.save(
                        (userAdditionRepository.findOneByUserAndSource(user, profile.source) ?: UserAddition()
                                .apply {
                                    this.user = user
                                    this.source = profile.source
                                })
                                .apply {
                                    this.imageUrl = profile.imageUrl
                                    this.name = profile.name ?: email
                                    this.details = profile.details
                                })
                userRepository.save(user)
            }
        }
    }
}