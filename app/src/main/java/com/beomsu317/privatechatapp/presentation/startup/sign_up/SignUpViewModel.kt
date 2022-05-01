package com.beomsu317.privatechatapp.presentation.startup.sign_up

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beomsu317.privatechatapp.common.Resource
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
class SignUpViewModel @Inject constructor(
    private val privateChatUseCases: PrivateChatUseCases,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) : ViewModel() {

    var state by mutableStateOf(SignUpState())
        private set

    private val _oneTimeEvent = Channel<OneTimeEvent>()
    val oneTimeEventFlow = _oneTimeEvent.receiveAsFlow()

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.SignUp -> {
                signUp(
                    displayName = event.displayName,
                    email = event.email,
                    password = event.password,
                    confirmPassword = event.confirmPassword,
                )
            }
        }
    }

    private fun signUp(
        displayName: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        viewModelScope.launch {
            if (displayName.isNullOrBlank()) {
                _oneTimeEvent.send(OneTimeEvent.ShowSnackbar("Username is empty"))
                return@launch
            }
            if (!validateEmailUseCase(email)) {
                _oneTimeEvent.send(OneTimeEvent.ShowSnackbar("Email is not valid"))
                return@launch
            }
            if (!validatePasswordUseCase(password) || !validatePasswordUseCase(confirmPassword)) {
                _oneTimeEvent.send(OneTimeEvent.ShowSnackbar("Password is not valid"))
                return@launch
            }
            if (password != confirmPassword) {
                _oneTimeEvent.send(OneTimeEvent.ShowSnackbar("Password and confirm password are not match"))
                return@launch
            }
            privateChatUseCases.signUpUseCase(
                displayName = displayName,
                email = email,
                password = password,
                confirmPassword = confirmPassword
            ).onEach { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _oneTimeEvent.send(OneTimeEvent.ShowSnackbar("Successfully registered"))
                        _oneTimeEvent.send(OneTimeEvent.SignedUp)
                        state = state.copy(isLoading = false)
                    }
                    is Resource.Error -> {
                        _oneTimeEvent.send(
                            OneTimeEvent.ShowSnackbar(
                                resource.message ?: "An unexpected error occured"
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

