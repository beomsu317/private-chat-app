package com.beomsu317.chat_domain.use_case

import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.beomsu317.chat_domain.repository.ChatRepository
import com.beomsu317.core.domain.model.Message
import com.beomsu317.core.domain.repository.CoreRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

class ConnectToServer @Inject constructor(
    private val chatRepository: ChatRepository,
    private val coreRepository: CoreRepository
) {

    suspend operator fun invoke(scope: CoroutineScope, onNotificate: (String, Message) -> Unit) {
        try {
            val token = coreRepository.getTokenFlow().first()
            if (token.isNullOrEmpty()) {
                return
            }
            if (!chatRepository.isConnected()) {
                chatRepository.connectToChatServer(
                    scope = scope,
                    onNotificate = onNotificate
                )
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}