package com.bbbrrr8877.totalrecall.topics

import com.bbbrrr8877.totalrecall.core.Core
import com.bbbrrr8877.totalrecall.core.Module
import com.bbbrrr8877.totalrecall.topics.data.ChosenTopicCache
import com.bbbrrr8877.totalrecall.topics.data.TopicsRepository
import com.bbbrrr8877.totalrecall.topics.data.cache.TopicCacheToList
import com.bbbrrr8877.totalrecall.topics.data.cache.TopicsCacheDataSource
import com.bbbrrr8877.totalrecall.topics.data.cloud.BaseCloudDataSource
import com.bbbrrr8877.totalrecall.topics.presentation.TopicsListCommunication
import com.bbbrrr8877.totalrecall.topics.presentation.TopicsViewModel

class TopicsModule(private val core: Core) : Module<TopicsViewModel> {
    override fun viewModel() = TopicsViewModel (
        core.navigation(),
        core.provideDispatchersList(),
        TopicsRepository.Base(
            ChosenTopicCache.Base(core.storage()),
            TopicsCacheDataSource.Base(
                core.roomDatabase().cardsDao(),
                TopicCacheToList()
            ),
            BaseCloudDataSource.Base(
                core.roomDatabase().cardsDao(),
                core
            )
        ),
        TopicsListCommunication.Base()
    )
}