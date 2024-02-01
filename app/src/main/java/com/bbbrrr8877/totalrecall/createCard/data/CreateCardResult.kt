package com.bbbrrr8877.totalrecall.createCard.data

import com.bbbrrr8877.totalrecall.cardsList.presentation.CardListScreen
import com.bbbrrr8877.totalrecall.createCard.presentation.CreateCardCommunication
import com.bbbrrr8877.totalrecall.createCard.presentation.CreateCardUiState
import com.bbbrrr8877.totalrecall.main.NavigationCommunication

interface CreateCardResult {

    fun map(
        communication: CreateCardCommunication,
        navigationCommunication: NavigationCommunication.Update
    )

    object Success : CreateCardResult {
        override fun map(
            communication: CreateCardCommunication,
            navigationCommunication: NavigationCommunication.Update
        ) = navigationCommunication.map(CardListScreen)
    }

    data class Failed(private val errorMessage: String) : CreateCardResult {
        override fun map(
            communication: CreateCardCommunication,
            navigationCommunication: NavigationCommunication.Update
        ) = communication.map(CreateCardUiState.Error(errorMessage))
    }
}