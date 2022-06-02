package com.beomsu317.friends_domain.use_case

import com.beomsu317.core.domain.repository.CoreRepository
import com.beomsu317.friends_domain.model.FriendWithPriority
import kotlinx.coroutines.flow.first
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val repository: CoreRepository
) {
    suspend operator fun invoke(friendWithPriority: FriendWithPriority) {
        try {
            val user = repository.getUserFlow().first()
            val updatedUserFriends = user.friends.map {
                if (friendWithPriority.id == it.id) {
                    it.copy(priority = friendWithPriority.priority)
                } else {
                    it
                }
            }.toSet()
            repository.updateUser(user = user.copy(friends = updatedUserFriends))
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}