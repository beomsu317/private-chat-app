package com.beomsu317.friends_domain.repository

import com.beomsu317.core.domain.model.Friend
import com.beomsu317.core.domain.model.UserFriend
import kotlinx.coroutines.flow.Flow

interface FriendsRepository {

    suspend fun getMyFriends(token: String, refresh: Boolean): Flow<Set<Friend>>

    suspend fun getAllFriends(token: String): Set<Friend>

    suspend fun addFriend(token: String, userFriend: UserFriend)

    suspend fun deleteFriend(token: String, friend: Friend)

    suspend fun searchFriends(searchText: String): List<Friend>
}