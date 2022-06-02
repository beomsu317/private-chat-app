package com.beomsu317.friends_domain.repository

import com.beomsu317.core.domain.model.Friend
import com.beomsu317.core.domain.model.UserFriend
import kotlinx.coroutines.flow.Flow

interface FriendsRepository {

    suspend fun getUserFriends(refresh: Boolean): Flow<Set<Friend>>

    suspend fun searchFriends(searchText: String): Set<Friend>

    suspend fun addFriend(userFriend: UserFriend)

    suspend fun deleteUserFriend(friend: Friend)

    suspend fun deleteUserFriendById(friendId: String)

    suspend fun getUserFriend(id: String): Friend

    suspend fun searchUserFriend(searchText: String): List<Friend>
}