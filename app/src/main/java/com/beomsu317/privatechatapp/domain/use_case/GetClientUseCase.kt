package com.beomsu317.privatechatapp.domain.use_case

import com.beomsu317.privatechatapp.domain.model.Client
import com.beomsu317.privatechatapp.domain.repository.PrivateChatRepository
import javax.inject.Inject

class GetClientUseCase @Inject constructor(
    private val repository: PrivateChatRepository
) {

    suspend operator fun invoke(): Client {
        return repository.getClient()
    }
}