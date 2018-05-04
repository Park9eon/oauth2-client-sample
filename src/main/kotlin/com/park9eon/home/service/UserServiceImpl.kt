package com.park9eon.home.service

import com.park9eon.home.dao.UserAdditionRepository
import com.park9eon.home.dao.UserRepository
import com.park9eon.home.dao.UserRoleRepository
import com.park9eon.home.domain.User
import com.park9eon.home.domain.UserAddition
import com.park9eon.home.model.Profile
import com.park9eon.home.model.Role
import com.park9eon.home.model.UserDetails
import com.park9eon.home.support.create
import org.springframework.security.core.context.SecurityContextHolder
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
    override fun findOrCreateByProfile(profile: Profile): User =
            "${profile.serviceName}:${profile.id}".let { additionId ->
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
                                    roles = mutableSetOf(userRoleRepository.create {
                                        this.user = user
                                        this.role = Role.ROLE_USER
                                    })
                                }
                            }
                }
                userAddition.run {
                    this.imageUrl = profile.imageUrl
                    this.name = profile.name ?: UNKNOWN_NAME
                    this.details = profile.details
                    userAdditionRepository.save(this).user
                }
            }

    @Transactional(readOnly = true)
    override fun findById(id: Long): User =
            userRepository.get(id)

    override fun currentUser(): User? =
            (SecurityContextHolder.getContext()?.authentication as? UserDetails)?.id?.let {
                User(it)
            }
}