package com.bbbrrr8877.totalrecall.topics.data

import com.bbbrrr8877.totalrecall.core.Save
import com.bbbrrr8877.totalrecall.topics.data.cache.TopicsCacheDataSource
import com.bbbrrr8877.totalrecall.topics.data.cloud.BaseCloudDataSource
import com.bbbrrr8877.totalrecall.topics.presentation.TopicInfo

interface TopicsRepository : Save<TopicInfo> {

    suspend fun data(): List<TopicList>
    suspend fun init(firstRun: Boolean)

    class Base(
        private val saveTopic: ChosenTopicCache.Save,
        private val cacheDataSource: TopicsCacheDataSource,
        private val cloudDataSource: BaseCloudDataSource
    ) : TopicsRepository {

        override suspend fun data(): List<TopicList> {
            val list = mutableListOf<TopicList>()
            val topics = cacheDataSource.topics()
            if (topics.isEmpty())
                list.add(TopicList.NoTopicsHint)
            else
                list.addAll(topics)
            return list
        }

        override fun save(data: TopicInfo) = saveTopic.save(data)
        override suspend fun init(firstRun: Boolean) = cloudDataSource.init(firstRun)
    }
}

