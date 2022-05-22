package com.beomsu317.chat_domain.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class StartupReceiver: BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        Log.d("TAG", "onReceive: StartupReceiver")
//        p0?.startService(p1)
    }
}