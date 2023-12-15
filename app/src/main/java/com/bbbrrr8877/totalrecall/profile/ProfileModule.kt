package com.bbbrrr8877.totalrecall.profile

import com.bbbrrr8877.totalrecall.core.Core
import com.bbbrrr8877.totalrecall.core.Module
import com.bbbrrr8877.totalrecall.profile.presentation.ProfileCommunication
import com.bbbrrr8877.totalrecall.profile.presentation.ProfileViewModel

class ProfileModule(private val core: Core) : Module<ProfileViewModel> {
    override fun viewModel() = ProfileViewModel(ProfileCommunication.Base(), core.navigation())
}