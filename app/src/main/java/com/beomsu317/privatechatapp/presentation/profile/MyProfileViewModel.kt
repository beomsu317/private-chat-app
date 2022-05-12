package com.beomsu317.privatechatapp.presentation.profile

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beomsu317.privatechatapp.common.Resource
import com.beomsu317.privatechatapp.domain.model.Client
import com.beomsu317.privatechatapp.domain.model.User
import com.beomsu317.privatechatapp.domain.use_case.GetClientUseCase
import com.beomsu317.privatechatapp.domain.use_case.PrivateChatUseCases
import com.beomsu317.privatechatapp.presentation.common.OneTimeEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(
    private val privateChatUseCases: PrivateChatUseCases,
    private val getClientUseCase: GetClientUseCase
): ViewModel() {

    var state by mutableStateOf(MyProfileState())
        private set

    private val _oneTimeEvent = Channel<OneTimeEvent>()
    val oneTimeEventFlow = _oneTimeEvent.receiveAsFlow()

    init {
        getProfile()
    }

    fun onEvent(event: MyProfileEvent) {
        when (event) {
            is MyProfileEvent.SignOut -> {
                signOut()
            }
            is MyProfileEvent.UploadProfileImage -> {
                uploadProfileImage(event.uri)
            }
        }
    }

    private fun getProfile() {
        viewModelScope.launch {
            privateChatUseCases.getProfileUseCase().onEach { resource ->
                when (resource) {
                    is Resource.Success -> {
                        state = state.copy(isLoading = false, user = getClientUseCase().user)
                    }
                    is Resource.Error -> {
                        _oneTimeEvent.send(OneTimeEvent.ShowSnackbar(resource.message ?: "An unexpected error occured"))
                        state = state.copy(isLoading = false)
                    }
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun signOut() {
        viewModelScope.launch {
            privateChatUseCases.signOutUseCase()
        }
    }

    private fun uploadProfileImage(uri: Uri?) {
        uri?.let {
            viewModelScope.launch {
                privateChatUseCases.uploadProfileImageUseCase(uri = uri).onEach { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            state = state.copy(user = getClientUseCase().user)
                            _oneTimeEvent.send(OneTimeEvent.ShowSnackbar("Successfully update"))
                        }
                        is Resource.Error -> {
                            _oneTimeEvent.send(OneTimeEvent.ShowSnackbar(resource.message ?: "An unknown error occured"))
                        }
                    }
                }.launchIn(viewModelScope)
            }
        } ?: return
    }
}