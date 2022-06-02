package com.beomsu317.friends_presentation.friends_list

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beomsu317.core.common.Resource
import com.beomsu317.core.domain.repository.CoreRepository
import com.beomsu317.core.domain.use_case.CoreUseCases
import com.beomsu317.core_ui.common.OneTimeEvent
import com.beomsu317.friends_domain.model.FriendWithPriority
import com.beomsu317.friends_domain.use_case.FriendsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendsListViewModel @Inject constructor(
    private val friendsUseCases: FriendsUseCases,
    private val coreUseCases: CoreUseCases
) : ViewModel() {

    var state by mutableStateOf(FriendsListState())

    private val _oneTimeEvent = Channel<OneTimeEvent>()
    val oneTimeEventFlow = _oneTimeEvent.receiveAsFlow()

    var searchText by mutableStateOf("")

    private var getFriendsJob: Job? = null

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
            getFriendsJob = friendsUseCases.getUserFriendsUseCase(refresh).onEach {
                Log.d("TAG", "getFriends: ${it}")
                val user = coreUseCases.getUserFlowUseCase().first()
                val sortedFriendsList = friendsUseCases.sortByPriorityUseCase(user.friends, it)
                if (searchText.isNullOrEmpty()) {
                    state = state.copy(friends = sortedFriendsList, isLoading = false)
                } else {
                    search()
                }
            }.catch { e ->
                _oneTimeEvent.send(OneTimeEvent.ShowSnackbar(e.localizedMessage))
                state = state.copy(isLoading = false)
            }.launchIn(viewModelScope)
        }
    }

    private fun search() {
        viewModelScope.launch {
            val friends = friendsUseCases.searchUserFriendUseCase(searchText).toSet()
            val user = coreUseCases.getUserFlowUseCase().first()
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