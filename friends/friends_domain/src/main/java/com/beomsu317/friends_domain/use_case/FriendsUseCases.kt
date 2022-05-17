package com.beomsu317.friends_domain.use_case

data class FriendsUseCases(
    val addFriendUseCase: AddFriendUseCase,
    val deleteFriendUseCase: DeleteFriendUseCase,
    val getAllFriendsUseCase: GetAllFriendsUseCase,
    val getMyFriendsUseCase: GetMyFriendsUseCase,
    val searchFriendsUseCase: SearchFriendsUseCase
)