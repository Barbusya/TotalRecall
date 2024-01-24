package com.bbbrrr8877.totalrecall.cardsList

import com.bbbrrr8877.totalrecall.cardsList.data.CardsListCloudDataSource
import com.bbbrrr8877.totalrecall.cardsList.data.CardsListRepository
import com.bbbrrr8877.totalrecall.cardsList.data.CardsNamesCache
import com.bbbrrr8877.totalrecall.cardsList.data.ChosenCardsCache
import com.bbbrrr8877.totalrecall.cardsList.presentation.CardsListCommunication
import com.bbbrrr8877.totalrecall.cardsList.presentation.CardsListViewModel
import com.bbbrrr8877.totalrecall.core.Core
import com.bbbrrr8877.totalrecall.core.Module

class CardsListModule(private val core: Core) : Module<CardsListViewModel> {

    override fun viewModel() = CardsListViewModel (
        core.navigation(),
        core.provideDispatchersList(),
        CardsListRepository.Base(
            ChosenCardsCache.Base(core.storage()),
            CardsListCloudDataSource.Base(
                CardsNamesCache.Base(core.storage()),
                core
            )
        ),
        CardsListCommunication.Base()
    )
}