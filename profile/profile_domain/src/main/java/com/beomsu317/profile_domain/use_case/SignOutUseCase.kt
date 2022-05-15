package com.beomsu317.profile_domain.use_case

import com.beomsu317.profile_domain.repository.ProfileRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke() {
        repository.signOut()
    }
}