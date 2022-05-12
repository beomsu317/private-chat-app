package com.beomsu317.privatechatapp.domain.use_case

data class PrivateChatUseCases(
    val signUpUseCase: SignUpUseCase,
    val signInUseCase: SignInUseCase,
    val getProfileUseCase: GetProfileUseCase,
    val isSignedInUseCase: IsSignedInUseCase,
    val signOutUseCase: SignOutUseCase,
    val uploadProfileImageUseCase: UploadProfileImageUseCase,
    val getFriendsUseCase: GetFriendsUseCase,
    val getAllFriendsUseCase: GetAllFriendsUseCase,
    val addFriendUseCase: AddFriendUseCase,
    val deleteFriendUseCase: DeleteFriendUseCase,
    val searchFriends: SearchFriendsUseCase
)