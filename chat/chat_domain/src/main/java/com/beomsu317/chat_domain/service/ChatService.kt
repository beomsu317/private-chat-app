package com.beomsu317.chat_domain.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class ChatService: Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("TAG", "onStartCommand: ")
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("TAG", "onCreate: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TAG", "onDestroy: ")
    }

}