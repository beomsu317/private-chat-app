package com.beomsu317.privatechatapp.presentation.startup.sign_in

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beomsu317.privatechatapp.common.Resource
import com.beomsu317.privatechatapp.domain.model.User
import com.beomsu317.privatechatapp.domain.use_case.PrivateChatUseCases
import com.beomsu317.privatechatapp.domain.use_case.ValidateEmailUseCase
import com.beomsu317.privatechatapp.domain.use_case.ValidatePasswordUseCase
import com.beomsu317.privatechatapp.presentation.common.OneTimeEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val privateChatUseCases: PrivateChatUseCases,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
) : ViewModel() {

    var state by mutableStateOf(SignInState())
        private set

    private val _oneTimeEvent = Channel<OneTimeEvent>()
    val oneTimeEventFlow = _oneTimeEvent.receiveAsFlow()

    fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.SignIn -> {
                signIn(event.email, event.password)
            }
        }
    }

    private fun signIn(email: String, password: String) {
        viewModelScope.launch {
            if (!validateEmailUseCase(email)) {
                _oneTimeEvent.send(OneTimeEvent.ShowSnackbar("Email is not valid"))
                return@launch
            }

            if (!validatePasswordUseCase(password)) {
                _oneTimeEvent.send(OneTimeEvent.ShowSnackbar("Password is not valid"))
                return@launch
            }

            privateChatUseCases.signInUseCase(email = email, password = password)
                .onEach { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            _oneTimeEvent.send(OneTimeEvent.ShowSnackbar("Successfully signed in"))
                            _oneTimeEvent.send(OneTimeEvent.SignedIn)
                            state = state.copy(isLoading = false)
                        }
                        is Resource.Error -> {
                            _oneTimeEvent.send(
                                OneTimeEvent.ShowSnackbar(
                                    resource.message ?: "An unknown error occured"
                                )
                            )
                            state = state.copy(isLoading = false)
                        }
                        is Resource.Loading -> {
                            state = state.copy(isLoading = true)
                        }
                    }
                }.launchIn(viewModelScope)
        }
    }
}