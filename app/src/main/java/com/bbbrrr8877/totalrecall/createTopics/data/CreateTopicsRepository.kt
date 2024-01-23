package com.bbbrrr8877.totalrecall.createTopics.data

import com.bbbrrr8877.totalrecall.topics.data.ChosenTopicCache
import com.bbbrrr8877.totalrecall.topics.data.MyTopicsNamesCache

interface CreateTopicsRepository {

    suspend fun create(name: String): CreateTopicResult
    fun contains(name: String): Boolean

    class Base(
        private val createTopicsCloudDataSource: CreateTopicsCloudDataSource,
        private val chosenTopicCache: ChosenTopicCache.Save,
        namesCache: MyTopicsNamesCache.Read
    ) : CreateTopicsRepository {

        private val namesCachedList = namesCache.read().map {
            it.lowercase()
        }

        override suspend fun create(name: String) = try {
            val topic = createTopicsCloudDataSource.createTopic(name)
            chosenTopicCache.save(topic)
            CreateTopicResult.Success
        } catch (e: Exception) {
            CreateTopicResult.Failed(e.message ?: "error")
        }

        override fun contains(name: String) =
            namesCachedList.contains(name.lowercase())
    }
}