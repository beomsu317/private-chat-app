package com.beomsu317.privatechatapp.presentation.friends.add_friends

sealed class AddFriendsEvent {
    data class AddFriend(val friendId: String, val priority: Int): AddFriendsEvent()
}
