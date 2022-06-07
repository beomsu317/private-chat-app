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
import com.beomsu317.core.domain.model.Message
import com.beomsu317.core.domain.model.Room
import com.beomsu317.core.domain.use_case.CoreUseCases
import com.beomsu317.core_ui.common.OneTimeEvent
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
class ChatRoomViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val chatUseCases: ChatUseCases,
    private val coreUseCases: CoreUseCases
) : ViewModel() {

    var state by mutableStateOf(ChatRoomState())
        private set

    private val _oneTimeEvent = Channel<OneTimeEvent>()
    val oneTimeEventFlow = _oneTimeEvent.receiveAsFlow()

    private var getMessagesJob: Job? = null

    init {
        savedStateHandle.get<String>("friendId")?.let { friendId ->
            getOrCreateRoom(friendId = friendId)
            getFriend(friendId = friendId)
            getUser()
        }
    }

    fun onEvent(event: ChatRoomEvent) {
        when (event) {
            is ChatRoomEvent.SendMessage -> {
                sendMessage(event.text)
            }
        }
    }

    private fun getOrCreateRoom(friendId: String) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            chatUseCases.createRoomUseCase(friendId).onEach { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val room = resource.data ?: Room()
                        getMessagesJob?.cancel()
                        getMessagesJob =
                            chatUseCases.getMessagesFlowUseCase(room.id).onEach { messages ->
                                val falseMessages = messages.filter { it.read == false }
                                if (falseMessages.size > 0) {
                                    readAllMessages(room.id)
                                }
                                state = state.copy(messages = messages, isLoading = false)
                            }.launchIn(viewModelScope)
                        state = state.copy(room = room)
                    }
                    is Resource.Error -> {
                        _oneTimeEvent.send(
                            OneTimeEvent.ShowSnackbar(
                                resource.message ?: "An unexpected error occured"
                            )
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun getFriend(friendId: String) {
        viewModelScope.launch {
            chatUseCases.getFriendUseCase(friendId = friendId).onEach { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val friend = resource.data ?: Friend()
                        state = state.copy(friend = friend)
                    }
                    is Resource.Error -> {
                        _oneTimeEvent.send(
                            OneTimeEvent.ShowSnackbar(
                                resource.message ?: "An unexpected error occured"
                            )
                        )
                    }
                    is Resource.Loading -> {

                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun getUser() {
        viewModelScope.launch {
            coreUseCases.getUserFlowUseCase().onEach {
                state = state.copy(user = it)
            }.launchIn(viewModelScope)
        }
    }

    private fun readAllMessages(roomId: String) {
        viewModelScope.launch {
            chatUseCases.readAllMessagesUseCase(roomId)
        }
    }

    private fun sendMessage(text: String) {
        viewModelScope.launch {
            val user = coreUseCases.getUserFlowUseCase().first()
            val message = Message(
                senderId = user.id,
                roomId = state.room.id,
                timestamp = System.currentTimeMillis(),
                displayName = user.displayName,
                message = text,
                photoUrl = user.photoUrl
            )
            chatUseCases.sendMessageUseCase(message).onEach { resource ->
                when (resource) {
                    is Resource.Success -> {

                    }
                    is Resource.Error -> {
                        _oneTimeEvent.send(OneTimeEvent.ShowSnackbar(resource.message ?: "An unknown error occured"))
                    }
                    is Resource.Loading -> {

                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}