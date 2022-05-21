package com.beomsu317.friends_domain.use_case

data class FriendsUseCases(
    val addFriendUseCase: AddFriendUseCase,
    val deleteFriendUseCase: DeleteFriendUseCase,
    val getAllFriendsUseCase: GetAllFriendsUseCase,
    val getMyFriendsUseCase: GetMyFriendsUseCase,
    val sortByPriorityUseCase: SortByPriorityUseCase,
    val updateUserUseCase: UpdateUserUseCase,
    val getUserFlowUseCase: GetUserFlowUseCase,
    val searchFriendUseCase: SearchFriendUseCase
)