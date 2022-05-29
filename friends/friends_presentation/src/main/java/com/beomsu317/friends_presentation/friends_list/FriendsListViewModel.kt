package com.beomsu317.friends_presentation.friends_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beomsu317.core.common.Resource
import com.beomsu317.core_ui.common.OneTimeEvent
import com.beomsu317.friends_domain.model.FriendWithPriority
import com.beomsu317.friends_domain.use_case.FriendsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendsListViewModel @Inject constructor(
    private val friendsUseCases: FriendsUseCases,
) : ViewModel() {

    var state by mutableStateOf(FriendsListState())

    private val _oneTimeEvent = Channel<OneTimeEvent>()
    val oneTimeEventFlow = _oneTimeEvent.receiveAsFlow()

    var searchText by mutableStateOf("")

    var getFriendsJob: Job? = null

    init {
        getFriends(true)
    }

    fun onEvent(event: FriendsListEvent) {
        when (event) {
            is FriendsListEvent.Search -> {
                search()
            }
            is FriendsListEvent.DeleteFriend -> {
                deleteFriend(friendId = event.friendId)
            }
            is FriendsListEvent.RefreshFriends -> {
                getFriends(event.refresh)
            }
            is FriendsListEvent.UpdateUser -> {
                updateUser(event.friendWithPriority)
            }
        }
    }

    private fun getFriends(refresh: Boolean) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            getFriendsJob?.cancel()
            getFriendsJob = friendsUseCases.getMyFriendsUseCase(refresh).onEach {
                val user = friendsUseCases.getUserFlowUseCase().first()
                val sortedFriendsList = friendsUseCases.sortByPriorityUseCase(user.friends, it)
                if (searchText.isNullOrEmpty()) {
                    state = state.copy(friends = sortedFriendsList, isLoading = false)
                } else {
                    search()
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun search() {
        viewModelScope.launch {
            val friends = friendsUseCases.searchUserFriendUseCase(searchText).toSet()
            val user = friendsUseCases.getUserFlowUseCase().first()
            val sortedFriendsList = friendsUseCases.sortByPriorityUseCase(user.friends, friends)
            state = state.copy(friends = sortedFriendsList, isLoading = false)
        }
    }

    private fun deleteFriend(friendId: String) {
        viewModelScope.launch {
            friendsUseCases.deleteFriendUseCase(friendId = friendId).onEach { resource ->
                when (resource) {
                    is Resource.Success -> {
                        state = state.copy(
                            friends = state.friends.filter { it.id != friendId },
                            isLoading = false
                        )
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

    private fun updateUser(friendWithPriority: FriendWithPriority) {
        viewModelScope.launch {
            friendsUseCases.updateUserUseCase(friendWithPriority)
            search()
        }
    }
}