package com.beomsu317.friends_presentation.add_friends

sealed class AddFriendsEvent {
    data class AddFriend(val friendId: String, val priority: Int): AddFriendsEvent()
    object RefreshAllFriends: AddFriendsEvent()
}
