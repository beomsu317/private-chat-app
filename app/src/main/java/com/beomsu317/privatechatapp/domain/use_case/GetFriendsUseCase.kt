package com.beomsu317.privatechatapp.domain.use_case

import com.beomsu317.privatechatapp.common.Resource
import com.beomsu317.privatechatapp.domain.model.Friend
import com.beomsu317.privatechatapp.domain.repository.PrivateChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class GetFriendsUseCase @Inject constructor(
    private val repository: PrivateChatRepository
) {
    suspend operator fun invoke(refresh: Boolean): Flow<Set<Friend>> =
        repository.getFriends(refresh = refresh)

}