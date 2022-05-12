package com.beomsu317.privatechatapp.presentation.friends.friends_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beomsu317.privatechatapp.common.Resource
import com.beomsu317.privatechatapp.domain.model.Friend
import com.beomsu317.privatechatapp.domain.use_case.PrivateChatUseCases
import com.beomsu317.privatechatapp.presentation.common.OneTimeEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendsListViewModel @Inject constructor(
    private val privateChatUseCases: PrivateChatUseCases
) : ViewModel() {

    var state by mutableStateOf(FriendsListState())

    private val _oneTimeEvent = Channel<OneTimeEvent>()
    val oneTimeEventFlow = _oneTimeEvent.receiveAsFlow()

    var searchJob: Job? = null

    init {
        getFriends(true)
    }

    fun onEvent(event: FriendsListEvent) {
        when (event) {
            is FriendsListEvent.Search -> {
                search(searchText = event.searchText)
            }
            is FriendsListEvent.DeleteFriend -> {
                deleteFriend(friend = event.friend)
            }
            is FriendsListEvent.RefreshFriends -> {
                getFriends(event.refresh)
            }
        }
    }

    private fun getFriends(refresh: Boolean) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            privateChatUseCases.getFriendsUseCase(refresh).onEach {
                state = state.copy(friends = it, isLoading = false)
            }.launchIn(viewModelScope)
        }
    }

    private fun search(searchText: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            privateChatUseCases.searchFriends(searchText).onEach { resource ->
                when (resource) {
                    is Resource.Success -> {
                        state = state.copy(isLoading = false, friends = resource.data?.toSet() ?: emptySet())
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

    private fun deleteFriend(friend: Friend) {
        viewModelScope.launch {
            privateChatUseCases.deleteFriendUseCase(friend = friend).onEach { resource ->
                when (resource) {
                    is Resource.Success -> {
                        state = state.copy(friends = state.friends - friend, isLoading = false)
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