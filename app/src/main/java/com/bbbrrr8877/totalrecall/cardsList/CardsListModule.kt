package com.bbbrrr8877.totalrecall.cardsList

import com.bbbrrr8877.totalrecall.cardsList.data.CardsListRepository
import com.bbbrrr8877.totalrecall.cardsList.data.ChosenCardCache
import com.bbbrrr8877.totalrecall.cardsList.data.cache.CardsCacheToList
import com.bbbrrr8877.totalrecall.cardsList.data.cache.CardsListCacheDataSource
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
            CardsListCacheDataSource.Base(
                core.roomDatabase().cardsDao(),
                CardsCacheToList()
            )
        ),
        CardsListCommunication.Base()
    )
}