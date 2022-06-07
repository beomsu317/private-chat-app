package com.beomsu317.chat_domain.use_case

data class ChatUseCases(
    val createRoomUseCase: CreateRoomUseCase,
    val getLastMessagesFlowUseCase: GetLastMessagesFlowUseCase,
    val getMessagesFlowUseCase: GetMessagesFlowUseCase,
    val getFriendUseCase: GetFriendUseCase,
    val sendMessageUseCase: SendMessageUseCase,
    val readAllMessagesUseCase: ReadAllMessagesUseCase,
    val getRecentMessagesUseCase: GetRecentMessagesUseCase,
    val leaveRoomUseCase: LeaveRoomUseCase,
    val connectToServerUseCase: ConnectToServerUseCase,
    val removeMessagesUseCase: RemoveMessagesUseCase
)
