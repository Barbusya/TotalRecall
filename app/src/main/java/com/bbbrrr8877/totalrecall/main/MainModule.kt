package com.bbbrrr8877.totalrecall.main

import com.bbbrrr8877.totalrecall.core.Core
import com.bbbrrr8877.totalrecall.core.Module

class MainModule(private val core: Core) : Module<MainViewModel> {

    override fun viewModel() = MainViewModel(core.navigation())

}