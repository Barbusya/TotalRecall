package com.bbbrrr8877.totalrecall.login

import com.bbbrrr8877.totalrecall.core.Core
import com.bbbrrr8877.totalrecall.core.Module
import com.bbbrrr8877.totalrecall.login.data.LoginCloudDataSource
import com.bbbrrr8877.totalrecall.login.data.LoginRepository
import com.bbbrrr8877.totalrecall.login.presentation.LoginCommunication
import com.bbbrrr8877.totalrecall.login.presentation.LoginViewModel

class LoginModule(private val core: Core) : Module<LoginViewModel> {

    override fun viewModel() = LoginViewModel(
        LoginRepository.Base(LoginCloudDataSource.Base(core)),
        core.provideDispatchersList(),
        core.manageResource(),
        LoginCommunication.Base(),
        core.navigation()
    )
}