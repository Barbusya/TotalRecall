package com.bbbrrr8877.totalrecall.login.data

import com.bbbrrr8877.totalrecall.login.presentation.LoginCommunication
import com.bbbrrr8877.totalrecall.login.presentation.LoginUiState
import com.bbbrrr8877.totalrecall.main.NavigationCommunication
import com.bbbrrr8877.totalrecall.profile.presentation.ProfileScreen
import com.bbbrrr8877.totalrecall.topics.presentation.TopicsScreen

interface LoginResult {

    fun map(communication: LoginCommunication, navigation: NavigationCommunication.Update)

    object Success : LoginResult {
        override fun map(
            communication: LoginCommunication,
            navigation: NavigationCommunication.Update
        ) = navigation.map(TopicsScreen)
    }

    data class Failed(private val message: String) : LoginResult {
        override fun map(
            communication: LoginCommunication,
            navigation: NavigationCommunication.Update
        ) = communication.map(LoginUiState.Error(message))
    }
}