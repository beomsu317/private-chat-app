package com.beomsu317.friends_data.repository

import android.util.Log
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
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toSet
import kotlinx.coroutines.withContext
import java.lang.Exception

class FriendsRepositoryImpl(
    private val api: PrivateChatApi,
    private val database: PrivateChatDatabase,
    private val appDataStore: AppDataStore,
    private val dispatcher: CoroutineDispatcher
) : FriendsRepository {

    override suspend fun getMyFriends(refresh: Boolean): Flow<Set<Friend>> {
        return withContext(dispatcher) {
            if (refresh) {
                val token = appDataStore.tokenFlow.first()
                val response = api.getMyFriends(auth = "Bearer ${token}")
                if (!response.isSuccessful) {
                    throw Exception(response.message())
                }
                val friends =
                    response.body()?.result?.friends ?: throw Exception("Get friends error occured")
                database.friendsDao().deleteAllFriend()
                database.friendsDao().insertFriends(friends.map { it.toEntity() }.toSet())
            }
            database.friendsDao().getFriends().map {
                it.map { it.toFriend() }.toSet()
            }
        }
    }

    override suspend fun getAllFriends(): Set<Friend> {
        return withContext(dispatcher) {
            val token = appDataStore.tokenFlow.first()
            val response = api.getAllFriends(auth = "Bearer ${token}")
            if (!response.isSuccessful) {
                throw Exception(response.message())
            }
            val friends =
                response.body()?.result?.friends ?: throw Exception("Get all friend error occured")
            friends.map { it.toFriend() }.toSet()
        }
    }

    override suspend fun addFriend(userFriend: UserFriend) {
        withContext(dispatcher) {
            val token = appDataStore.tokenFlow.first()
            val response = api.addFriend(
                auth = "Bearer ${token}",
                request = AddFriendRequest(listOf(userFriend.toDto()).toSet())
            )
            if (!response.isSuccessful) {
                throw Exception(response.message())
            }
            val result = response.body()?.result ?: throw Exception("Add friend error occured")
            database.friendsDao().insertFriends(result.friends.map { it.toEntity() }.toSet())
            val user = appDataStore.userFlow.first()
            appDataStore.updateUser(user.copy(friends = user.friends + userFriend))
        }
    }

    override suspend fun deleteFriend(friend: Friend) {
        withContext(dispatcher) {
            val token = appDataStore.tokenFlow.first()
            val response = api.deleteFriend(
                auth = "Bearer ${token}",
                request = DeleteFriendRequest(friends = setOf(friend.id))
            )
            if (!response.isSuccessful) {
                throw Exception(response.message())
            }
            database.friendsDao().deleteFriend(friendEntity = friend.toEntity())
            val user = appDataStore.userFlow.first()
            appDataStore.updateUser(user.copy(friends = user.friends.filter { friend.id != it.id }
                .toSet()))
        }
    }

    override suspend fun deleteFriendById(friendId: String) {
        withContext(dispatcher) {
            val token = appDataStore.tokenFlow.first()
            val response = api.deleteFriend(
                auth = "Bearer ${token}",
                request = DeleteFriendRequest(friends = setOf(friendId))
            )
            if (!response.isSuccessful) {
                throw Exception(response.message())
            }
            database.friendsDao().deleteFriendById(friendId = friendId)
            val user = appDataStore.userFlow.first()
            appDataStore.updateUser(user.copy(friends = user.friends.filter { friendId != it.id }
                .toSet()))
        }
    }

    override suspend fun getFriend(id: String): Friend {
        return withContext(dispatcher) {
            database.friendsDao().getFriend(id).toFriend()
        }
    }

    override suspend fun searchFriend(searchText: String): List<Friend> {
        return withContext(dispatcher) {
            database.friendsDao().searchFriends(searchText).map { it.toFriend() }
        }
    }
}