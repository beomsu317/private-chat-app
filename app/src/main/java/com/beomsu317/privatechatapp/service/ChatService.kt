package com.beomsu317.privatechatapp.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.beomsu317.chat_domain.repository.ChatRepository
import com.beomsu317.chat_domain.use_case.ChatUseCases
import com.beomsu317.core.domain.repository.CoreRepository
import com.beomsu317.core.domain.use_case.CoreUseCases
import com.beomsu317.privatechatapp.activity.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@AndroidEntryPoint
class ChatService : Service() {

    companion object {
        const val NOTIFICATION_ID = 10
        const val CHANNEL_ID = "notification_channel"
        const val CHANNEL_NAME = "Chat Notification"
    }


    @Inject
    lateinit var chatRepository: ChatRepository

    @Inject
    lateinit var coreUseCases: CoreUseCases

    @Inject
    lateinit var chatUseCases: ChatUseCases

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(notificationChannel)

            val notification = NotificationCompat.Builder(this, CHANNEL_ID).build()
            startForeground(NOTIFICATION_ID, notification)
            stopForeground(false)
        }

        scope.launch {
            chatUseCases.connectToServer(
                scope = scope,
                onNotificate = { displayName, message ->
                    scope.launch {
                        val intent = Intent(this@ChatService, MainActivity::class.java).apply {
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        }
                        val pendingIntent: PendingIntent = PendingIntent.getActivity(this@ChatService, 0, intent,
                            PendingIntent.FLAG_IMMUTABLE
                        )

                        val settings = coreUseCases.getSettingsFlowUseCase().first()
                        val user = coreUseCases.getUserFlowUseCase().first()
                        if (settings.notifications && user.id != message.senderId) {
                            val notification =
                                NotificationCompat.Builder(this@ChatService, CHANNEL_ID)
                                    .setContentTitle(displayName)
                                    .setContentText(message.message)
                                    .setSmallIcon(com.beomsu317.core.R.drawable.logo)
                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                    .setAutoCancel(true)
                                    .setContentIntent(pendingIntent)
                            startForeground(NOTIFICATION_ID, notification.build())
                            stopForeground(false)
                        }
                    }
                }
            )
        }

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        chatRepository.closeChatServer()
        job.cancel()
    }

}