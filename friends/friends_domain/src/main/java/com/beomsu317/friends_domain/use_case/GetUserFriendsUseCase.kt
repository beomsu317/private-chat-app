package com.beomsu317.friends_domain.use_case

import com.beomsu317.core.domain.model.Friend
import com.beomsu317.friends_domain.repository.FriendsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class GetUserFriendsUseCase @Inject constructor(
    private val repository: FriendsRepository
) {
    suspend operator fun invoke(refresh: Boolean): Flow<Set<Friend>> {
        return repository.getUserFriends(refresh = refresh)
    }
}