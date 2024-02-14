package com.bbbrrr8877.totalrecall.cardsList

import com.bbbrrr8877.totalrecall.cardsList.data.CardsListCloudDataSource
import com.bbbrrr8877.totalrecall.cardsList.data.CardsListRepository
import com.bbbrrr8877.totalrecall.cardsList.data.ChosenCardCache
import com.bbbrrr8877.totalrecall.cardsList.data.LearningOrder
import com.bbbrrr8877.totalrecall.cardsList.presentation.CardsListCommunication
import com.bbbrrr8877.totalrecall.cardsList.presentation.CardsListViewModel
import com.bbbrrr8877.totalrecall.core.Core
import com.bbbrrr8877.totalrecall.core.Module
import com.bbbrrr8877.totalrecall.topics.data.ChosenTopicCache

class CardsListModule(private val core: Core) : Module<CardsListViewModel> {

    override fun viewModel() = CardsListViewModel (
        core.navigation(),
        core.provideDispatchersList(),
        CardsListRepository.Base(
            ChosenCardCache.Base(core.storage()),
            ChosenTopicCache.Base(core.storage()),
            CardsListCloudDataSource.Base(
                core,
                LearningOrder.Base()
            ),

            ),
        CardsListCommunication.Base(),
    )
}