package com.bbbrrr8877.totalrecall.profile.presentation

import androidx.lifecycle.ViewModel
import com.bbbrrr8877.totalrecall.core.Communication
import com.bbbrrr8877.totalrecall.core.GoBack
import com.bbbrrr8877.totalrecall.core.Init
import com.bbbrrr8877.totalrecall.login.presentation.LoginScreen
import com.bbbrrr8877.totalrecall.main.NavigationCommunication
import com.bbbrrr8877.totalrecall.main.Screen
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class ProfileViewModel(
    private val communication: ProfileCommunication,
    private val navigationCommunication: NavigationCommunication.Update
) : ViewModel(), Init, Communication.Observe<ProfileUiState>, GoBack {

    override fun liveData() = communication.liveData()

    fun signOut() {
        Firebase.auth.signOut()
        navigationCommunication.map(LoginScreen)
    }

    override fun init(firstRun: Boolean) {
        if (Firebase.auth.currentUser == null)
            navigationCommunication.map(LoginScreen)
        else {
            val currentUser = Firebase.auth.currentUser!!
            communication.map(
                ProfileUiState.Base(
                    currentUser.email!!,
                    currentUser.displayName ?: "",
                )
            )
        }
    }

    override fun goBack() {
        navigationCommunication.map(Screen.Pop)
    }
}