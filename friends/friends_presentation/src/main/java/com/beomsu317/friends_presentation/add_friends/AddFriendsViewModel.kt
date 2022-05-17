package com.beomsu317.friends_presentation.add_friends

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beomsu317.core.common.Resource
import com.beomsu317.core.domain.data_store.AppDataStore
import com.beomsu317.core.domain.model.UserFriend
import com.beomsu317.core_ui.common.OneTimeEvent
import com.beomsu317.friends_domain.use_case.FriendsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddFriendsViewModel @Inject constructor(
    private val friendsUseCases: FriendsUseCases,
    private val appDataStore: AppDataStore
) : ViewModel() {

    var state by mutableStateOf(AddFriendsState())
        private set

    private val _oneTimeEvent = Channel<OneTimeEvent>()
    val oneTimeEventFlow = _oneTimeEvent.receiveAsFlow()

    init {
        getAllFriends()
    }

    fun onEvent(event: AddFriendsEvent) {
        when (event) {
            is AddFriendsEvent.AddFriend -> {
                addFriend(friendId = event.friendId, priority = event.priority)
            }
            is AddFriendsEvent.RefreshAllFriends -> {
                getAllFriends()
            }
        }
    }

    private fun getAllFriends() {
        viewModelScope.launch {
            val token = appDataStore.getToken()
            friendsUseCases.getAllFriendsUseCase(token = token).onEach { resource ->
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
            val token = appDataStore.getToken()
            friendsUseCases.addFriendUseCase(token = token, userFriend = UserFriend(friendId, priority)).onEach { resource ->
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