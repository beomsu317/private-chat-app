package com.beomsu317.chat_presentation.chat_room_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beomsu317.chat_domain.use_case.ChatUseCases
import com.beomsu317.core.common.Resource
import com.beomsu317.core_ui.common.OneTimeEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatRoomListViewModel @Inject constructor(
    private val chatUseCases: ChatUseCases
) : ViewModel() {

    var state by mutableStateOf(ChatRoomListState())
        private set

    private val _oneTimeEvent = Channel<OneTimeEvent>()
    val oneTimeEventFlow = _oneTimeEvent.receiveAsFlow()

    init {
        getRecentMessageList()
    }

    fun onEvent(event: ChatRoomListEvent) {
        when (event) {
            is ChatRoomListEvent.LeaveRoom -> {
                leaveRoom(event.roomId)
            }
        }
    }

    private fun getRecentMessageList() {
        chatUseCases.getRecentMessagesUseCase()
            .onEach {
                state = state.copy(recentMessage = it)
            }
            .catch { e ->
                _oneTimeEvent.send(OneTimeEvent.ShowSnackbar(e.localizedMessage))
            }
            .launchIn(viewModelScope)
    }

    private fun leaveRoom(roomId: String) {
        viewModelScope.launch {
            chatUseCases.leaveRoomUseCase(roomId).onEach { resource ->
                when (resource) {
                    is Resource.Success -> {
                        state = state.copy(isLoading = false)
                    }
                    is Resource.Error -> {
                        state = state.copy(isLoading = false)
                        _oneTimeEvent.send(OneTimeEvent.ShowSnackbar(resource.message ?: "An unexpected error occured"))
                    }
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}