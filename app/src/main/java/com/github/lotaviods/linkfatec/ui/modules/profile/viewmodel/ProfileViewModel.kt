package com.github.lotaviods.linkfatec.ui.modules.profile.viewmodel

import androidx.lifecycle.ViewModel
import com.github.lotaviods.linkfatec.data.repository.UserRepository
import com.github.lotaviods.linkfatec.model.User

class ProfileViewModel(private val userRepository: UserRepository): ViewModel() {
    fun logoutUser(user: User?) {
        user?.let { profile -> userRepository.deleteUser(profile) }
    }
}