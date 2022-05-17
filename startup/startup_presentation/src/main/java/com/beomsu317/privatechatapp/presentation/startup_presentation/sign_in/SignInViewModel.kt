package com.beomsu317.privatechatapp.presentation.startup_presentation.sign_in

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beomsu317.core.common.Resource
import com.beomsu317.core.domain.use_case.ValidateEmailUseCase
import com.beomsu317.core.domain.use_case.ValidatePasswordUseCase
import com.beomsu317.core_ui.common.OneTimeEvent
import com.beomsu317.startup_domain.use_case.StartupUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val startupUseCases: StartupUseCases,
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

            startupUseCases.signInUseCase(email = email, password = password)
                .onEach { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            _oneTimeEvent.send(OneTimeEvent.ShowSnackbar("Successfully signed in"))
                            state = state.copy(isLoading = false)
                            _oneTimeEvent.send(OneTimeEvent.SignedIn)
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