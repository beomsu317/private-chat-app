package com.beomsu317.chat_presentation.chat_room_list

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beomsu317.chat_domain.use_case.ChatUseCases
import com.beomsu317.core.common.Resource
import com.beomsu317.core.domain.model.Room
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

    private fun getRecentMessageList() {

        chatUseCases.getRecentMessagesUseCase()
            .onEach {
                state = state.copy(recentMessage = it)
            }
            .launchIn(viewModelScope)
    }
}