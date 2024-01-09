package com.bbbrrr8877.totalrecall.topics.data

import com.bbbrrr8877.totalrecall.core.Save
import com.bbbrrr8877.totalrecall.topics.presentation.ReloadWithError
import com.bbbrrr8877.totalrecall.topics.presentation.TopicInfo

interface TopicsRepository : InitialReloadCallback, Save<TopicInfo> {

    suspend fun data(): List<Topic>

    class Base(
        private val saveTopic: ChosenTopicCache.Save,
        private val cloudDataSource: TopicsCloudDataSource
    ) : TopicsRepository {

        override suspend fun data() = try {
            val list = mutableListOf<Topic>()
            val topicsList = cloudDataSource.topicsList()
            if (topicsList.isEmpty())
                list.add(Topic.NoTopicsHint)
            else
                list.addAll(topicsList)
            list
        } catch (e: Exception) {
            listOf(Topic.Error(e.message ?: "error"))
        }

        override fun init(reload: ReloadWithError) = cloudDataSource.init(reload)

        override fun save(data: TopicInfo) = saveTopic.save(data)
    }
}

interface InitialReloadCallback {

    fun init(reload: ReloadWithError)
}