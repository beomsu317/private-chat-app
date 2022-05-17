package com.beomsu317.friends_data.repository

import com.beomsu317.core.domain.model.Friend
import com.beomsu317.core.domain.model.UserFriend
import com.beomsu317.friends_data.remote.PrivateChatApi
import com.beomsu317.friends_data.remote.request.AddFriendRequest
import com.beomsu317.friends_data.remote.request.DeleteFriendRequest
import com.beomsu317.friends_domain.repository.FriendsRepository
import com.beomsu317.friends_data.local.PrivateChatDatabase
import com.beomsu317.core.data.mapper.toDto
import com.beomsu317.core.domain.data_store.AppDataStore
import com.beomsu317.friends_data.mapper.toEntity
import com.beomsu317.friends_data.mapper.toFriend
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.lang.Exception

class FriendsRepositoryImpl(
    private val api: PrivateChatApi,
    private val database: PrivateChatDatabase,
    private val appDataStore: AppDataStore
) : FriendsRepository {

    override suspend fun getMyFriends(token: String, refresh: Boolean): Flow<Set<Friend>> {
        if (refresh) {
            val response = api.getMyFriends(auth = "Bearer ${token}")
            if (!response.isSuccessful) {
                throw Exception(response.message())
            }
            val friends =
                response.body()?.result?.friends ?: throw Exception("Get friends error occured")
            database.friendsDao().deleteAllFriend()
            database.friendsDao().insertFriends(friends.map { it.toEntity() }.toSet())
        }
        return database.friendsDao().getFriends().map { it.map { it.toFriend() }.toSet() }
    }

    override suspend fun getAllFriends(token: String): Set<Friend> {
        val response = api.getAllFriends(auth = "Bearer ${token}")
        if (!response.isSuccessful) {
            throw Exception(response.message())
        }
        val friends =
            response.body()?.result?.friends ?: throw Exception("Get all friend error occured")
        return friends.map { it.toFriend() }.toSet()
    }

    override suspend fun addFriend(token: String, userFriend: UserFriend) {
        val response = api.addFriend(
            auth = "Bearer ${token}",
            request = AddFriendRequest(listOf(userFriend.toDto()).toSet())
        )
        if (!response.isSuccessful) {
            throw Exception(response.message())
        }
        val result = response.body()?.result ?: throw Exception("Add friend error occured")
        database.friendsDao().insertFriends(result.friends.map { it.toEntity() }.toSet())
        val user = appDataStore.getUser()
        appDataStore.updateUser(user.copy(friends = user.friends + userFriend))
    }

    override suspend fun deleteFriend(token: String, friend: Friend) {
        val response = api.deleteFriend(
            auth = "Bearer ${token}",
            request = DeleteFriendRequest(friends = setOf(friend.id))
        )
        if (!response.isSuccessful) {
            throw Exception(response.message())
        }
        database.friendsDao().deleteFriend(friendEntity = friend.toEntity())
        val user = appDataStore.getUser()
        appDataStore.updateUser(user.copy(friends = user.friends.filter { friend.id != it.id }.toSet()))
    }

    override suspend fun searchFriends(searchText: String): List<Friend> {
        val friends = database.friendsDao().searchFriends(searchText)
        return friends.map { it.toFriend() }
    }
}