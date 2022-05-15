package com.beomsu317.privatechatapp.presentation.startup_presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beomsu317.core.domain.data_store.AppDataStore
import com.beomsu317.core_ui.common.OneTimeEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val appDataStore: AppDataStore
) : ViewModel() {

    private val _oneTimeEvent = Channel<OneTimeEvent>()
    val oneTimeEventFlow = _oneTimeEvent.receiveAsFlow()

    init {
        isSignedIn()
    }

    private fun isSignedIn() {
        viewModelScope.launch {
            val tokenDeffered = async { appDataStore.getToken() }
            val delayDeffered = async { delay(1000L) }

            delayDeffered.await()
            val token = tokenDeffered.await()
            if (token.isEmpty()) {
                _oneTimeEvent.send(OneTimeEvent.NeedSignIn)
            } else {
                _oneTimeEvent.send(OneTimeEvent.SignedIn)
            }
        }
    }
}