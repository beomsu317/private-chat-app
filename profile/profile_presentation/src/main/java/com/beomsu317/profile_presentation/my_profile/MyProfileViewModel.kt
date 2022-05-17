package com.beomsu317.profile_presentation.my_profile

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beomsu317.core.common.Resource
import com.beomsu317.core.domain.data_store.AppDataStore
import com.beomsu317.core.domain.model.User
import com.beomsu317.core_ui.common.OneTimeEvent
import com.beomsu317.profile_domain.use_case.ProfileUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases,
    private val appDataStore: AppDataStore
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
            appDataStore.userFlow.onEach {
                state = state.copy(user = it)
            }.launchIn(viewModelScope)
        }
    }

    private fun signOut() {
        viewModelScope.launch {
            profileUseCases.signOutUseCase()
        }
    }

    private fun uploadProfileImage(uri: Uri?) {
        uri?.let {
            viewModelScope.launch {
                val token = appDataStore.getToken()
                profileUseCases.uploadProfileImageUseCase(token = token, uri = uri).onEach { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            state = state.copy(user = state.user.copy(photoUrl = resource.data ?: ""), isLoading = false)
                            _oneTimeEvent.send(OneTimeEvent.ShowSnackbar("Successfully update"))
                        }
                        is Resource.Error -> {
                            _oneTimeEvent.send(OneTimeEvent.ShowSnackbar(resource.message ?: "An unknown error occured"))
                            state = state.copy(isLoading = false)
                        }
                        is Resource.Loading -> {
                            state = state.copy(isLoading = true)
                        }
                    }
                }.launchIn(viewModelScope)
            }
        } ?: return
    }
}