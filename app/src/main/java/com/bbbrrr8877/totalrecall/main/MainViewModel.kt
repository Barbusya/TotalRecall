package com.bbbrrr8877.totalrecall.main

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.bbbrrr8877.totalrecall.core.Init
import com.bbbrrr8877.totalrecall.login.presentation.LoginScreen

class MainViewModel(
    private val navigationCommunication: NavigationCommunication.Mutable
) : ViewModel(), NavigationCommunication.Observe, Init {

    override fun observe(owner: LifecycleOwner, observer: Observer<Screen>) =
        navigationCommunication.observe(owner, observer)

    override fun init(firstRun: Boolean) {
        if (firstRun) navigationCommunication.map(LoginScreen)
    }
}