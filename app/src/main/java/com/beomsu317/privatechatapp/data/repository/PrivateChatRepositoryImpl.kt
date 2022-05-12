package com.beomsu317.privatechatapp.data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.beomsu317.privatechatapp.data.local.data_store.ClientDataStore
import com.beomsu317.privatechatapp.data.local.room.PrivateChatDatabase
import com.beomsu317.privatechatapp.data.local.room.entity.FriendEntity
import com.beomsu317.privatechatapp.data.local.room.entity.toFriend
import com.beomsu317.privatechatapp.data.remote.PrivateChatApi
import com.beomsu317.privatechatapp.data.remote.dto.toEntity
import com.beomsu317.privatechatapp.data.remote.dto.toFriend
import com.beomsu317.privatechatapp.data.remote.request.AddFriendRequest
import com.beomsu317.privatechatapp.data.remote.request.DeleteFriendRequest
import com.beomsu317.privatechatapp.data.remote.request.UserLoginRequest
import com.beomsu317.privatechatapp.data.remote.request.UserRegisterRequest
import com.beomsu317.privatechatapp.domain.model.*
import com.beomsu317.privatechatapp.domain.repository.PrivateChatRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.lang.Exception
import javax.inject.Inject

class PrivateChatRepositoryImpl @Inject constructor(
    private val api: PrivateChatApi,
    private val database: PrivateChatDatabase,
    private val clientDataStore: ClientDataStore,
    private val context: Context,
    private val client: Client
) : PrivateChatRepository {
    override suspend fun registerUser(
        displayName: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        val response = api.registerUser(
            UserRegisterRequest(
                displayName = displayName,
                email = email,
                password = password,
                confirmPassword = confirmPassword
            )
        )
        if (!response.isSuccessful) {
            throw Exception(response.message())
        }
    }

    override suspend fun loginUser(email: String, password: String) {
        val response = api.loginUser(
            UserLoginRequest(
                email = email,
                password = password
            )
        )
        if (!response.isSuccessful) {
            throw Exception(response.message())
        } else {
            val result = response.body()?.result ?: throw Exception("login user error occured")
            val token = result.token
            clientDataStore.updateClient(
                Client(
                    token = token
                )
            )
        }
    }

    override suspend fun getProfile(): User {
        val response = api.getProfile()
        if (!response.isSuccessful) {
            throw Exception(response.message())
        }
        val result = response.body()?.result ?: throw Exception("get profile error occured")
        val userDto = result.user
        clientDataStore.updateClient(
            client.copy(
                user = User(
                    id = userDto.id,
                    email = userDto.email,
                    displayName = userDto.displayName,
                    photoUrl = userDto.photoUrl,
                    friends = userDto.friends.map { it.toEntity() }.toSet(),
                    rooms = userDto.rooms
                )
            )
        )
        return userDto.toEntity()
    }

    override suspend fun isSigned(): Boolean {
        val client = clientDataStore.dataStoreFlow.firstOrNull()
        if (client?.token != null) {
            return true
        }
        return false
    }

    override suspend fun signOut() {
        clientDataStore.updateClient(
            Client()
        )
    }

    override suspend fun uploadProfileImage(uri: Uri): String {
        val inputStream =
            context.contentResolver.openInputStream(uri) ?: throw Exception("Inputstream is null")
        val requestBody =
            inputStream?.readBytes()?.toRequestBody(contentType = "image/*".toMediaType())
        val part = MultipartBody.Part.createFormData("profile", "profile", requestBody)
        inputStream.close()

        val response = api.uploadProfileImage(part)
        if (!response.isSuccessful) {
            throw Exception(response.message())
        }
        val result =
            response.body()?.result ?: throw Exception("upload profile image error occured")
        clientDataStore.updateClient(
            client.copy(
                user = client.user.copy(photoUrl = result.photoUrl)
            )
        )
        return result.photoUrl
    }

    override suspend fun getFriends(refresh: Boolean): Flow<Set<Friend>> {
        if (refresh) {
            val response = api.getFriends()
            if (!response.isSuccessful) {
                throw Exception(response.message())
            }
            val result = response.body()?.result ?: throw Exception("get friend error occured")
            database.dao.deleteAllFriend()
            database.dao.insertFriends(result.friends.map { it.toEntity() }.toSet())
        }
        return database.dao.getFriends().map { it.map { it.toFriend() }.toSet() }
    }

    override suspend fun getAllFriends(): Set<Friend> {
        val response = api.getAllFriends()
        if (!response.isSuccessful) {
            throw Exception(response.message())
        }
        val result = response.body()?.result ?: throw Exception("get all friend error occured")
        return result.friends.map { it.toFriend() }.toSet()
    }

    override suspend fun addFriend(userFriend: UserFriend) {
        val response = api.addFriend(AddFriendRequest(listOf(userFriend.toDto()).toSet()))
        if (!response.isSuccessful) {
            throw Exception(response.message())
        }
        val result = response.body()?.result ?: throw Exception("add friend error occured")
        database.dao.insertFriends(result.friends.map { it.toEntity() }.toSet())
        clientDataStore.updateClient(
            client = client.copy(
                user = client.user.copy(friends = client.user.friends + userFriend),
            )
        )
    }

    override suspend fun deleteFriend(friend: Friend) {
        val deleteFriend =
            client.user.friends.filter { it.id == friend.id }.map { it.toDto() }.toSet()
        val response = api.deleteFriend(DeleteFriendRequest(deleteFriend))
        if (!response.isSuccessful) {
            throw Exception(response.message())
        }
        database.dao.deleteFriend(friendEntity = friend.toEntity())
        clientDataStore.updateClient(
            client = client.copy(
                user = client.user.copy(friends = client.user.friends - deleteFriend.map { it.toEntity() }),
            ),
        )
    }

    override suspend fun searchFriends(searchText: String): List<Friend> {
        val friends = database.dao.searchFriends(searchText)
        return friends.map { it.toFriend() }
    }

    override suspend fun getClient(): Client {
        return client
    }
}