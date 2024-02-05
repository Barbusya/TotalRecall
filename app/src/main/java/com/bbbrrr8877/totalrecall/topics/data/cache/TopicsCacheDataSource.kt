package com.bbbrrr8877.totalrecall.topics.data.cache

import com.bbbrrr8877.totalrecall.cardsList.data.cache.CardsDao
import com.bbbrrr8877.totalrecall.topics.data.TopicList

interface TopicsCacheDataSource {

    suspend fun topics(): List<TopicList>

    class Base(
        private val dao: CardsDao,
        private val cacheToList: TopicCache.Mapper<TopicList.MyTopicsList>
    ) : TopicsCacheDataSource {
        override suspend fun topics(): List<TopicList> = dao.allTopics().map { it.map(cacheToList) }
    }
}