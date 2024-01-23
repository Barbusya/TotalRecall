package com.bbbrrr8877.totalrecall.cardsList.presentation

import com.bbbrrr8877.totalrecall.core.Communication

interface CardsListCommunication : Communication.Mutable<CardsListUiState> {

    class Base : Communication.Abstract<CardsListUiState>(), CardsListCommunication
}