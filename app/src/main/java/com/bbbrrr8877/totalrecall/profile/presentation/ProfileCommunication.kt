package com.bbbrrr8877.totalrecall.profile.presentation

import com.bbbrrr8877.totalrecall.core.Communication

interface ProfileCommunication : Communication.Mutable<ProfileUiState> {
    class Base : Communication.Abstract<ProfileUiState>(), ProfileCommunication
}