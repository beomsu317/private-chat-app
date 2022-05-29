package com.beomsu317.privatechatapp

import android.app.Application
import com.beomsu317.privatechatapp.service.ChatService
import com.beomsu317.core.common.startService
import com.beomsu317.core.domain.use_case.CoreUseCases
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.first

@HiltAndroidApp
class PrivateChatApp(): Application()