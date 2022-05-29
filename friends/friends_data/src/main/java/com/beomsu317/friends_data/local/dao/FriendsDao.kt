package com.beomsu317.friends_data.local.dao

import androidx.room.*
import com.beomsu317.friends_data.local.entity.FriendEntity
import com.beomsu317.friends_data.local.entity.UserFriendEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FriendsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserFriend(userFriendEntity: UserFriendEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserFriends(userFriendEntities: Set<UserFriendEntity>)

    @Delete
    suspend fun deleteUserFriend(userFriendEntity: UserFriendEntity)

    @Query("DELETE FROM userfriendentity WHERE id = :friendId")
    suspend fun deleteUserFriendById(friendId: String)

    @Query("DELETE FROM userfriendentity")
    suspend fun deleteAllUserFriends()

    @Query("SELECT * FROM userfriendentity")
    fun getUserFriends(): Flow<List<UserFriendEntity>>

    @Query("SELECT * FROM userfriendentity WHERE id = :id")
    suspend fun getUserFriend(id: String): UserFriendEntity

    @Query("SELECT * FROM userfriendentity WHERE displayName LIKE '%' || :searchText || '%'")
    fun searchUserFriends(searchText: String): List<UserFriendEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFriends(friendEntity: List<FriendEntity>)

    @Delete
    suspend fun deleteFriend(friendEntity: FriendEntity)

    @Query("SELECT * FROM friendentity WHERE displayName LIKE '%' || :searchText || '%'")
    suspend fun searchFriends(searchText: String): List<FriendEntity>
}