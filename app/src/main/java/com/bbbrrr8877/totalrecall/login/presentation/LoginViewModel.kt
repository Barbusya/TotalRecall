package com.bbbrrr8877.totalrecall.login.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.bbbrrr8877.totalrecall.core.BaseViewModel
import com.bbbrrr8877.totalrecall.core.Communication
import com.bbbrrr8877.totalrecall.core.DispatchersList
import com.bbbrrr8877.totalrecall.core.Init
import com.bbbrrr8877.totalrecall.core.ManageResource
import com.bbbrrr8877.totalrecall.login.data.LoginRepository
import com.bbbrrr8877.totalrecall.main.NavigationCommunication

class LoginViewModel(
    private val repository: LoginRepository,
    dispatcherList: DispatchersList,
    private val manageResource: ManageResource,
    private val communication: LoginCommunication,
    private val navigation: NavigationCommunication.Update,
) : BaseViewModel(dispatcherList), Init, Communication.Observe<LoginUiState> {

    override fun observe(owner: LifecycleOwner, observer: Observer<LoginUiState>) {
        communication.observe(owner, observer)
    }

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