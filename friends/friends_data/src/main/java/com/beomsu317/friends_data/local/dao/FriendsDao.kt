package com.beomsu317.friends_data.local.dao

import androidx.room.*
import com.beomsu317.friends_data.local.entity.FriendEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FriendsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFriend(friendEntity: FriendEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFriends(friendEntities: Set<FriendEntity>)

    @Delete
    suspend fun deleteFriend(friendEntity: FriendEntity)

    @Query("DELETE FROM friendentity")
    suspend fun deleteAllFriend()

    @Query("SELECT * FROM friendentity")
    fun getFriends(): Flow<List<FriendEntity>>

    @Query("SELECT * FROM friendentity WHERE displayName LIKE '%' || :searchText || '%'")
    suspend fun searchFriends(searchText: String): List<FriendEntity>
}