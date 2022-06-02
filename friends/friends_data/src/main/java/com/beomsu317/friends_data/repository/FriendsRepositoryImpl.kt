package com.beomsu317.friends_data.repository

import com.beomsu317.core.domain.model.Friend
import com.beomsu317.core.domain.model.UserFriend
import com.beomsu317.friends_data.remote.PrivateChatApi
import com.beomsu317.friends_data.remote.request.AddFriendRequest
import com.beomsu317.friends_data.remote.request.DeleteFriendRequest
import com.beomsu317.friends_domain.repository.FriendsRepository
import com.beomsu317.friends_data.local.FriendsDatabase
import com.beomsu317.core.data.mapper.toDto
import com.beomsu317.core.domain.data_store.AppDataStore
import com.beomsu317.friends_data.mapper.toUserFriendEntity
import com.beomsu317.friends_data.mapper.toFriend
import com.beomsu317.friends_data.mapper.toFriendEntity
import com.beomsu317.friends_data.remote.request.GetSearchFriendsRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import java.lang.Exception

class FriendsRepositoryImpl(
    private val api: PrivateChatApi,
    private val database: FriendsDatabase,
    private val appDataStore: AppDataStore,
    private val dispatcher: CoroutineDispatcher
) : FriendsRepository {

    override suspend fun getUserFriends(refresh: Boolean): Flow<Set<Friend>> {
        return withContext(dispatcher) {
            if (refresh) {
                val token = appDataStore.tokenFlow.first()
                val response = api.getMyFriends(auth = "Bearer ${token}")
                if (!response.isSuccessful) {
                    throw Exception(response.message())
                }
                val friends =
                    response.body()?.result?.friends ?: throw Exception("Get friends error occured")
                database.friendsDao().deleteAllUserFriends()
                database.friendsDao()
                    .insertUserFriends(friends.map { it.toUserFriendEntity() }.toSet())
            }
            database.friendsDao().getUserFriends().map {
                it.map { it.toFriend() }.toSet()
            }
        }
    }

    override suspend fun searchFriends(searchText: String): Set<Friend> {
        val user = appDataStore.userFlow.first()

        val token = appDataStore.tokenFlow.first()
        val response = api.getAllFriends(
            auth = "Bearer ${token}",
            request = GetSearchFriendsRequest(searchText)
        )
        if (!response.isSuccessful) {
            throw Exception(response.message())
        }
        val friends =
            response.body()?.result?.friends ?: throw Exception("Get all friend error occured")
        val friendEntities = friends.map { it.toFriendEntity() }
        database.friendsDao().insertFriends(friendEntities)
        return database.friendsDao().searchFriends(searchText).map { it.toFriend() }.filter {
            val friendsId = user.friends.map { it.id }
            !friendsId.contains(it.id)
        }.toSet()
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
            database.friendsDao()
                .insertUserFriends(result.friends.map { it.toUserFriendEntity() }.toSet())
            val user = appDataStore.userFlow.first()
            appDataStore.updateUser(user.copy(friends = user.friends + userFriend))
        }
    }

    override suspend fun deleteUserFriend(friend: Friend) {
        withContext(dispatcher) {
            val token = appDataStore.tokenFlow.first()
            val response = api.deleteFriend(
                auth = "Bearer ${token}",
                request = DeleteFriendRequest(friends = setOf(friend.id))
            )
            if (!response.isSuccessful) {
                throw Exception(response.message())
            }
            database.friendsDao().deleteUserFriend(userFriendEntity = friend.toUserFriendEntity())
            val user = appDataStore.userFlow.first()
            appDataStore.updateUser(user.copy(friends = user.friends.filter { friend.id != it.id }
                .toSet()))
        }
    }

    override suspend fun deleteUserFriendById(friendId: String) {
        withContext(dispatcher) {
            val token = appDataStore.tokenFlow.first()
            val response = api.deleteFriend(
                auth = "Bearer ${token}",
                request = DeleteFriendRequest(friends = setOf(friendId))
            )
            if (!response.isSuccessful) {
                throw Exception(response.message())
            }
            database.friendsDao().deleteUserFriendById(friendId = friendId)
            val user = appDataStore.userFlow.first()
            appDataStore.updateUser(user.copy(friends = user.friends.filter { friendId != it.id }
                .toSet()))
        }
    }

    override suspend fun getUserFriend(id: String): Friend {
        return withContext(dispatcher) {
            database.friendsDao().getUserFriend(id).toFriend()
        }
    }

    override suspend fun searchUserFriend(searchText: String): List<Friend> {
        return withContext(dispatcher) {
            database.friendsDao().searchUserFriends(searchText).map { it.toFriend() }
        }
    }
}