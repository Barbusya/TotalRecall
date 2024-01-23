package com.bbbrrr8877.totalrecall.createTopics

import com.bbbrrr8877.totalrecall.core.Core
import com.bbbrrr8877.totalrecall.core.Module
import com.bbbrrr8877.totalrecall.createTopics.data.CreateTopicsCloudDataSource
import com.bbbrrr8877.totalrecall.createTopics.data.CreateTopicsRepository
import com.bbbrrr8877.totalrecall.createTopics.presentation.CreateTopicsCommunication
import com.bbbrrr8877.totalrecall.createTopics.presentation.CreateTopicsViewModel
import com.bbbrrr8877.totalrecall.topics.data.ChosenTopicCache
import com.bbbrrr8877.totalrecall.topics.data.MyTopicsNamesCache

class CreateTopicsModule(private val core: Core) : Module<CreateTopicsViewModel> {
    override fun viewModel() = CreateTopicsViewModel(
        CreateTopicsRepository.Base(
            CreateTopicsCloudDataSource.Base(core),
            ChosenTopicCache.Base(core.storage()),
            MyTopicsNamesCache.Base(core.storage())
        ),
        core.manageResource(),
        CreateTopicsCommunication.Base(),
        core.navigation(),
        core.provideDispatchersList()
    )
}