package com.beomsu317.profile_presentation.my_profile

import android.net.Uri

sealed class MyProfileEvent {
    object SignOut: MyProfileEvent()
    data class UploadProfileImage(val uri: Uri?): MyProfileEvent()
}