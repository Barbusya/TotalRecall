package com.bbbrrr8877.totalrecall.login.presentation

import com.bbbrrr8877.android.BaseViewModel
import com.bbbrrr8877.android.DispatchersList
import com.bbbrrr8877.android.ManageResource
import com.bbbrrr8877.common.Init
import com.bbbrrr8877.totalrecall.core.Communication
import com.bbbrrr8877.totalrecall.login.data.LoginRepository
import com.bbbrrr8877.totalrecall.main.NavigationCommunication

class LoginViewModel(
    private val repository: LoginRepository,
    dispatcherList: DispatchersList,
    private val manageResource: ManageResource,
    private val communication: LoginCommunication,
    private val navigation: NavigationCommunication.Update,
) : BaseViewModel(dispatcherList), Init, Communication.Observe<LoginUiState> {

    override fun liveData() = communication.liveData()

    override fun init(firstRun: Boolean) {
        if (firstRun) {
            if (repository.userNotLoggedIn())
                communication.map(LoginUiState.Initial)
            else
                login()
        }
    }

    fun handleResult(authResult: AuthResultWrapper) = handle({
        repository.handleResult(authResult)
    }) {
        it.map(communication, navigation)
    }

    fun login() = communication.map(LoginUiState.Auth(manageResource))

}