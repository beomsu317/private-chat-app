package com.beomsu317.friends_domain.use_case

import com.beomsu317.core.domain.model.Friend
import com.beomsu317.friends_domain.repository.FriendsRepository
import javax.inject.Inject

class SearchUserFriendUseCase @Inject constructor(
    private val repository: FriendsRepository
) {
    suspend operator fun invoke(searchText: String): List<Friend> {
        return repository.searchUserFriend(searchText)
    }
}