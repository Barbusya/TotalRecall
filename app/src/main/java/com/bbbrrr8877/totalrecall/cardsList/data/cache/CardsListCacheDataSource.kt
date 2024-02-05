package com.bbbrrr8877.totalrecall.cardsList.data.cache

import com.bbbrrr8877.totalrecall.cardsList.data.CardsList
import com.bbbrrr8877.totalrecall.topics.presentation.TopicInfo

interface CardsListCacheDataSource {

    suspend fun cards(topicInfo: TopicInfo): List<CardsList>

    class Base(
        private val dao: CardsDao,
        private val cacheToList: CardsCacheToList
    ) : CardsListCacheDataSource {

        private val cardsList = mutableListOf<CardsList>()

        override suspend fun cards(topicInfo: TopicInfo): List<CardsList> {
            val topicName = topicInfo.name()
            val sourceList = dao.allCards()
            val list = sourceList
                .filter { it.topic == topicName }
                .map { it.map(cacheToList) }
            cardsList.addAll(list)

            return cardsList
        }

    }
}