package com.bbbrrr8877.totalrecall.cardsList.presentation

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

    override fun liveData() = communication.liveData()

    override fun toolbarText() = cardsListRepository.toolbarText()

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

    override fun reset(cardInfo: CardInfo) {
        handle({ cardsListRepository.reset(cardInfo) }) {
            reload()
        }
    }

    override fun learned(cardInfo: CardInfo) {
        handle({ cardsListRepository.learned(cardInfo) }) {
            reload()
        }
    }

    override fun error(message: String) = communication.map(CardsListUiState.Error(message))

    override fun showProfile() = navigation.map(ProfileScreen)

    override fun create() = navigation.map(CreateCardScreen)

}

interface CardsListViewModelActions : Init, Communication.Observe<CardsListUiState>,
    ReloadWithError, ShowProfile, Create, GoBack, SwipeListener, ToolbarText

interface SwipeListener : LearnedCard, ResetCard

interface LearnedCard {
    fun learned(cardInfo: CardInfo)
}

interface ResetCard {
    fun reset(cardInfo: CardInfo)
}

interface ToolbarText {
    fun toolbarText(): String
}
