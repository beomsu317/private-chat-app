package com.beomsu317.chat_presentation.chat_room

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beomsu317.chat_domain.use_case.ChatUseCases
import com.beomsu317.core.common.Resource
import com.beomsu317.core.domain.model.Friend
import com.beomsu317.core.domain.model.Room
import com.beomsu317.core_ui.common.OneTimeEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ChatRoomViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val chatUseCases: ChatUseCases
) : ViewModel() {

    var state by mutableStateOf(ChatRoomState())
        private set

    private val _oneTimeEvent = Channel<OneTimeEvent>()
    val oneTimeEventFlow = _oneTimeEvent.receiveAsFlow()

    init {
        savedStateHandle.get<String>("friend")?.let {
            val friend = Json.decodeFromString<Friend>(it)
            state = state.copy(friend = friend)
            createRoom(friend)
        }
    }

    private fun createRoom(friend: Friend) {
        viewModelScope.launch {
            chatUseCases.createRoomUseCase(friend.id).onEach { resource ->
                when (resource) {
                    is Resource.Error -> {
                        _oneTimeEvent.send(OneTimeEvent.ShowSnackbar(resource.message ?: "An unexpected error occured"))
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}