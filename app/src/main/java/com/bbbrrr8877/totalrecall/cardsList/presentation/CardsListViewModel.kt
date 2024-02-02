package com.bbbrrr8877.totalrecall.cardsList.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.bbbrrr8877.totalrecall.cardsList.data.CardsListRepository
import com.bbbrrr8877.totalrecall.core.BaseViewModel
import com.bbbrrr8877.totalrecall.core.Communication
import com.bbbrrr8877.totalrecall.core.Create
import com.bbbrrr8877.totalrecall.core.DispatchersList
import com.bbbrrr8877.totalrecall.core.GoBack
import com.bbbrrr8877.totalrecall.core.Init
import com.bbbrrr8877.totalrecall.core.ShowProfile
import com.bbbrrr8877.totalrecall.createCard.presentation.CreateCardScreen
import com.bbbrrr8877.totalrecall.main.NavigationCommunication
import com.bbbrrr8877.totalrecall.profile.presentation.ProfileScreen
import com.bbbrrr8877.totalrecall.topics.presentation.ReloadWithError
import com.bbbrrr8877.totalrecall.topics.presentation.TopicsListScreen

class CardsListViewModel(
    private val navigation: NavigationCommunication.Update,
    dispatchersList: DispatchersList,
    private val cardsListRepository: CardsListRepository,
    private val communication: CardsListCommunication,
) : BaseViewModel(dispatchersList), CardsListViewModelActions {

    override fun observe(owner: LifecycleOwner, observer: Observer<CardsListUiState>) {
        communication.observe(owner, observer)
    }

    override fun init(firstRun: Boolean) {
        communication.map(CardsListUiState.Progress)
        cardsListRepository.init(this)
    }

    override fun reload() {
        handle({ cardsListRepository.data() }) {
            communication.map(CardsListUiState.Base(it))
        }
    }


    override fun goBack() = navigation.map(TopicsListScreen)

    override fun error(message: String) = communication.map(CardsListUiState.Error(message))

    override fun showProfile() = navigation.map(ProfileScreen)

    override fun create() = navigation.map(CreateCardScreen)
}

interface CardsListViewModelActions : Init, Communication.Observe<CardsListUiState>,
    ReloadWithError, ShowProfile, Create, GoBack
