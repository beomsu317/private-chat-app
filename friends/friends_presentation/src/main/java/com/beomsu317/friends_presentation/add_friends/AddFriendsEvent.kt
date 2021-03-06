package com.beomsu317.friends_presentation.add_friends

sealed class AddFriendsEvent {
    data class AddFriend(val friendId: String): AddFriendsEvent()
    object SearchFriends: AddFriendsEvent()
}
