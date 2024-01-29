package com.bbbrrr8877.totalrecall.topics.data

import com.bbbrrr8877.totalrecall.core.InitialReloadCallback
import com.bbbrrr8877.totalrecall.core.Save
import com.bbbrrr8877.totalrecall.topics.presentation.ReloadWithError
import com.bbbrrr8877.totalrecall.topics.presentation.TopicInfo

interface TopicsRepository : InitialReloadCallback, Save<TopicInfo> {

    suspend fun data(): List<TopicList>

    class Base(
        private val saveTopic: ChosenTopicCache.Save,
        private val cloudDataSource: TopicsCloudDataSource
    ) : TopicsRepository {

        override suspend fun data() = try {
            val list = mutableListOf<TopicList>()
            val myOwnTopics = cloudDataSource.topics()
            if (myOwnTopics.isEmpty())
                list.add(TopicList.NoTopicsHint)
            else
                list.addAll(myOwnTopics)
            list
        } catch (e: Exception) {
            listOf(TopicList.Error(e.message ?: "error"))
        }

        override fun init(reload: ReloadWithError) = cloudDataSource.init(reload)

        override fun save(data: TopicInfo) = saveTopic.save(data)
    }
}

