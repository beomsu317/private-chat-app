package com.beomsu317.privatechatapp.presentation.startup.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beomsu317.privatechatapp.domain.use_case.PrivateChatUseCases
import com.beomsu317.privatechatapp.presentation.common.OneTimeEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val privateChatUseCases: PrivateChatUseCases
): ViewModel() {

    private val _oneTimeEvent = Channel<OneTimeEvent>()
    val oneTimeEventFlow = _oneTimeEvent.receiveAsFlow()

    init {
        isSignedIn()
    }

    private fun isSignedIn() {
        viewModelScope.launch {
            val isSignedIn = privateChatUseCases.isSignedInUseCase()
            delay(1000L)
            if (isSignedIn) {
                _oneTimeEvent.send(OneTimeEvent.SignedIn)
            } else {
                _oneTimeEvent.send(OneTimeEvent.NeedSignIn)
            }
        }
    }

}