package com.beomsu317.friends_presentation.add_friends

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beomsu317.core.common.Resource
import com.beomsu317.core.domain.model.UserFriend
import com.beomsu317.core_ui.common.OneTimeEvent
import com.beomsu317.friends_domain.use_case.FriendsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class AddFriendsViewModel @Inject constructor(
    private val friendsUseCases: FriendsUseCases,
) : ViewModel() {

    var state by mutableStateOf(AddFriendsState())
        private set

    private val _oneTimeEvent = Channel<OneTimeEvent>()
    val oneTimeEventFlow = _oneTimeEvent.receiveAsFlow()

    var searchText by mutableStateOf("")

    fun onEvent(event: AddFriendsEvent) {
        when (event) {
            is AddFriendsEvent.AddFriend -> {
                addFriend(friendId = event.friendId)
            }
            is AddFriendsEvent.SearchFriends -> {
                searchFriends()
            }
        }
    }

    private val handler = CoroutineExceptionHandler { _, e ->
        viewModelScope.launch {
            _oneTimeEvent.send(OneTimeEvent.ShowSnackbar(e.localizedMessage))
        }
    }

    private fun addFriend(friendId: String) {
        viewModelScope.launch {
            friendsUseCases.addFriendUseCase(userFriend = UserFriend(friendId, 2))
                .onEach { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            val friends = state.friends.filter {
                                it.id != friendId
                            }
                            state = state.copy(friends = friends.toSet(), isLoading = false)
                            _oneTimeEvent.send(
                                OneTimeEvent.ShowSnackbar("Successfully added")
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

    private fun searchFriends() {
        viewModelScope.launch(handler) {
            state = state.copy(friends = friendsUseCases.searchFriendsUseCase(searchText = searchText))
        }
    }
}