package com.bbbrrr8877.totalrecall.createCard

import com.bbbrrr8877.totalrecall.core.Core
import com.bbbrrr8877.totalrecall.core.Module
import com.bbbrrr8877.totalrecall.createCard.data.CreateCardCloudDataSource
import com.bbbrrr8877.totalrecall.createCard.data.CreateCardRepository
import com.bbbrrr8877.totalrecall.createCard.presentation.CreateCardCommunication
import com.bbbrrr8877.totalrecall.createCard.presentation.CreateCardViewModel
import com.bbbrrr8877.totalrecall.topics.data.ChosenTopicCache

class CreateCardModule(private val core: Core) : Module<CreateCardViewModel> {

    override fun viewModel() = CreateCardViewModel(
        CreateCardRepository.Base(
            ChosenTopicCache.Base(core.storage()),
            CreateCardCloudDataSource.Base(core)
        ),
        CreateCardCommunication.Base(),
        core.navigation(),
        core.provideDispatchersList()
    )
}