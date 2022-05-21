package com.beomsu317.friends_domain.repository

import com.beomsu317.core.domain.model.Friend
import com.beomsu317.core.domain.model.UserFriend
import kotlinx.coroutines.flow.Flow

interface FriendsRepository {

    suspend fun getMyFriends(refresh: Boolean): Flow<Set<Friend>>

    suspend fun getAllFriends(): Set<Friend>

    suspend fun addFriend(userFriend: UserFriend)

    suspend fun deleteFriend(friend: Friend)

    suspend fun deleteFriendById(friendId: String)

    suspend fun getFriend(id: String): Friend

    suspend fun searchFriend(searchText: String): List<Friend>
}