package com.beomsu317.privatechatapp.presentation.startup_presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beomsu317.core.domain.use_case.GetTokenUseCase
import com.beomsu317.core_ui.common.OneTimeEvent
import com.beomsu317.startup_domain.use_case.StartupUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getTokenUseCase: GetTokenUseCase
) : ViewModel() {

    private val _oneTimeEvent = Channel<OneTimeEvent>()
    val oneTimeEventFlow = _oneTimeEvent.receiveAsFlow()

    init {
        isSignedIn()
    }

    private fun isSignedIn() {
        viewModelScope.launch {
            val tokenDeffered = async { getTokenUseCase() }
            val delayDeffered = async { delay(1000L) }

            delayDeffered.await()
            val token = tokenDeffered.await()
            if (token.isNullOrEmpty()) {
                _oneTimeEvent.send(OneTimeEvent.NeedSignIn)
            } else {
                _oneTimeEvent.send(OneTimeEvent.SignedIn)
            }
        }
    }
}