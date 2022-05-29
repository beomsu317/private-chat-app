package com.beomsu317.core.common

import android.content.Context
import android.content.Intent
import android.os.Build

fun <T> Context.startService(cls: Class<T>) {
    Intent(this, cls).also {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(it)
        } else {
            startService(it)
        }
    }
}