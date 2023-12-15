package com.bbbrrr8877.totalrecall.login.presentation

import com.bbbrrr8877.totalrecall.core.Communication

interface LoginCommunication : Communication.Mutable<LoginUiState> {
    class Base : Communication.Abstract<LoginUiState>(), LoginCommunication
}