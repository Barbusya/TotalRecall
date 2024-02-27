package com.bbbrrr8877.totalrecall.createCard.presentation

import com.bbbrrr8877.android.BaseViewModel
import com.bbbrrr8877.android.DispatchersList
import com.bbbrrr8877.common.CreateUiActions
import com.bbbrrr8877.common.GoBack
import com.bbbrrr8877.totalrecall.cardsList.presentation.CardListScreen
import com.bbbrrr8877.totalrecall.core.Communication
import com.bbbrrr8877.totalrecall.createCard.data.CreateCardRepository
import com.bbbrrr8877.totalrecall.main.NavigationCommunication

class CreateCardViewModel(
    private val repository: CreateCardRepository,
    private val communication: CreateCardCommunication,
    private val navigation: NavigationCommunication.Update,
    dispatchersList: DispatchersList
) : BaseViewModel(dispatchersList), CreateCardActions {

    override fun liveData() = communication.liveData()

    override fun goBack() = navigation.map(CardListScreen)

    override fun createCard(answer: String, clue: String) {
        communication.map(CreateCardUiState.Progress)
        handle({ repository.createCard(answer, clue) }) {
            it.map(communication, navigation)
        }
    }

    override fun checkText(name: String) {
        communication.map(CreateCardUiState.CanCreateCard)
    }

    override fun disableCreate() {
        communication.map(CreateCardUiState.CanNotCreateCard)
    }
}

interface CreateCardActions : CreateUiActions, GoBack, Communication.Observe<CreateCardUiState> {

    fun createCard(answer: String, clue: String = "")
}