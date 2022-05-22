package com.beomsu317.privatechatapp

import android.app.Application
import android.content.Intent
import com.beomsu317.chat_domain.service.ChatService
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PrivateChatApp: Application() {
    override fun onCreate() {
        super.onCreate()
//        Intent(this, ChatService::class.java).also {
//            startService(it)
//        }
    }
}