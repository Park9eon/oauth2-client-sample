package com.park9eon.home.repository

import com.park9eon.home.model.UserAddition
import org.springframework.data.jpa.repository.JpaRepository


/**
 * Initial version by: park9eon
 * Initial version created on: 08/04/2018
 */
interface UserAdditionRepository : JpaRepository<UserAddition, String> {
}