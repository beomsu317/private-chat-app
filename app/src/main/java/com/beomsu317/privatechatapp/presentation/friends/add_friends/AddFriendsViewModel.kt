package com.beomsu317.privatechatapp.presentation.friends.add_friends

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beomsu317.privatechatapp.common.Resource
import com.beomsu317.privatechatapp.domain.model.UserFriend
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
class AddFriendsViewModel @Inject constructor(
    private val privateChatUseCases: PrivateChatUseCases,
    private val getClientUseCase: GetClientUseCase
) : ViewModel() {

    var state by mutableStateOf(AddFriendsState())
        private set

    private val _oneTimeEvent = Channel<OneTimeEvent>()
    val oneTimeEventFlow = _oneTimeEvent.receiveAsFlow()

    init {
        getFriends()
    }

    fun onEvent(event: AddFriendsEvent) {
        when (event) {
            is AddFriendsEvent.AddFriend -> {
                addFriend(friendId = event.friendId, priority = event.priority)
            }
        }
    }

    private fun getFriends() {
        viewModelScope.launch {
            privateChatUseCases.getAllFriendsUseCase().onEach { resource ->
                when (resource) {
                    is Resource.Success -> {
                        state = state.copy(friends = resource.data ?: emptySet(), isLoading = false)
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

    private fun addFriend(friendId: String, priority: Int) {
        viewModelScope.launch {
            privateChatUseCases.addFriendUseCase(UserFriend(friendId, priority)).onEach { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val friends = state.friends.filter {
                            it.id != friendId
                        }
                        state = state.copy(friends = friends.toSet(), isLoading = false)
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