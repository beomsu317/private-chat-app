package com.beomsu317.friends_domain.use_case

import com.beomsu317.core.domain.model.Friend
import com.beomsu317.friends_domain.repository.FriendsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMyFriendsUseCase @Inject constructor(
    private val repository: FriendsRepository
) {
    suspend operator fun invoke(token: String, refresh: Boolean): Flow<Set<Friend>> {
        return repository.getMyFriends(token = token, refresh = refresh)
    }

}