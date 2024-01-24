package com.bbbrrr8877.totalrecall.createTopics.data

import com.bbbrrr8877.totalrecall.cardsList.presentation.CardListScreen
import com.bbbrrr8877.totalrecall.createTopics.presentation.CreateTopicUiState
import com.bbbrrr8877.totalrecall.createTopics.presentation.CreateTopicsCommunication
import com.bbbrrr8877.totalrecall.main.NavigationCommunication

interface CreateTopicResult {

    fun map(
        communication: CreateTopicsCommunication,
        navigationCommunication: NavigationCommunication.Update
    )

    object Success : CreateTopicResult {
        override fun map(
            communication: CreateTopicsCommunication,
            navigationCommunication: NavigationCommunication.Update
        ) = navigationCommunication.map(CardListScreen)

    }

    data class Failed(private val errorMessage: String) : CreateTopicResult {
        override fun map(
            communication: CreateTopicsCommunication,
            navigationCommunication: NavigationCommunication.Update
        ) = communication.map(CreateTopicUiState.Error(errorMessage))
    }
}