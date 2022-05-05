package com.beomsu317.privatechatapp.presentation.profile

import android.net.Uri

sealed class MyProfileEvent {
    object SignOut: MyProfileEvent()
    data class UploadProfileImage(val uri: Uri?): MyProfileEvent()
}