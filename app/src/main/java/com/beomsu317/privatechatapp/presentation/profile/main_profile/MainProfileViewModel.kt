package com.beomsu317.privatechatapp.presentation.profile.main_profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beomsu317.privatechatapp.common.Resource
import com.beomsu317.privatechatapp.domain.model.Client
import com.beomsu317.privatechatapp.domain.model.User
import com.beomsu317.privatechatapp.domain.use_case.PrivateChatUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainProfileViewModel @Inject constructor(
    private val privateChatUseCases: PrivateChatUseCases,
): ViewModel() {

    init {
        viewModelScope.launch {
            privateChatUseCases.getProfileUseCase().onEach { resource ->
                when (resource) {
                    is Resource.Success -> {
                        Log.d("TAG", "${resource.data}")
                    }
                    is Resource.Error -> {
                        Log.d("TAG", "${resource.message}")
                    }
                    is Resource.Loading -> {
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}