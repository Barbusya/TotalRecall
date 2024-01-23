package com.bbbrrr8877.totalrecall.cardsList.presentation

import com.bbbrrr8877.totalrecall.cardsList.data.CardsListRepository
import com.bbbrrr8877.totalrecall.core.BaseViewModel
import com.bbbrrr8877.totalrecall.core.Communication
import com.bbbrrr8877.totalrecall.core.DispatchersList
import com.bbbrrr8877.totalrecall.core.Init
import com.bbbrrr8877.totalrecall.main.NavigationCommunication
import com.bbbrrr8877.totalrecall.topics.presentation.ReloadWithError

class CardsListViewModel(
    private val navigation: NavigationCommunication.Update,
    dispatchersList: DispatchersList,
    private val cardsListRepository: CardsListRepository,
    private val communication: CardsListCommunication,
) : BaseViewModel(dispatchersList), CardsListViewModelActions {
    override fun init(firstRun: Boolean) {
        TODO("Not yet implemented")
    }

    override fun reload() {
        TODO("Not yet implemented")
    }

    override fun error(message: String) {
        TODO("Not yet implemented")
    }
}

interface CardsListViewModelActions : Init, Communication.Observe<CardsListUiState>, ReloadWithError