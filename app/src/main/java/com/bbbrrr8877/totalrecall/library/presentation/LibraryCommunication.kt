package com.bbbrrr8877.totalrecall.library.presentation

import com.bbbrrr8877.totalrecall.core.Communication

interface LibraryCommunication : Communication.Mutable<LibraryUiState> {
    class Base : Communication.Abstract<LibraryUiState>(), LibraryCommunication
}