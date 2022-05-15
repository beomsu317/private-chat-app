package com.beomsu317.privatechatapp.domain.repository

import android.net.Uri
import com.beomsu317.privatechatapp.domain.model.Client
import com.beomsu317.privatechatapp.domain.model.Friend
import com.beomsu317.privatechatapp.domain.model.User
import com.beomsu317.privatechatapp.domain.model.UserFriend
import kotlinx.coroutines.flow.Flow

interface PrivateChatRepository {

    suspend fun registerUser(displayName: String, email: String, password: String, confirmPassword: String)

    suspend fun loginUser(email: String, password: String)

    suspend fun isSigned(): Boolean

    suspend fun getProfile(): User

    suspend fun signOut()

    suspend fun uploadProfileImage(uri: Uri): String

    suspend fun getFriends(refresh: Boolean): Flow<Set<Friend>>

    suspend fun getAllFriends(): Set<Friend>

    suspend fun getClient(): Client

    suspend fun addFriend(userFriend: UserFriend)

    suspend fun deleteFriend(friend: Friend)

    suspend fun searchFriends(searchText: String): List<Friend>
}