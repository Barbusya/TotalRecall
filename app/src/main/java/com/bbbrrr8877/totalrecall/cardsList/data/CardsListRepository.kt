package com.bbbrrr8877.totalrecall.cardsList.data

import com.bbbrrr8877.totalrecall.cardsList.data.cache.CardsListCacheDataSource
import com.bbbrrr8877.totalrecall.cardsList.data.cloud.CardsListCloudDataSource
import com.bbbrrr8877.totalrecall.cardsList.presentation.CardInfo
import com.bbbrrr8877.totalrecall.core.InitialReloadCallback
import com.bbbrrr8877.totalrecall.core.Save
import com.bbbrrr8877.totalrecall.topics.data.ChosenTopicCache
import com.bbbrrr8877.totalrecall.topics.presentation.ReloadWithError

interface CardsListRepository : InitialReloadCallback, Save<CardInfo> {

    suspend fun data(): List<CardsList>

    class Base(
        private val saveCards: ChosenCardCache.Save,
        private val readTopic: ChosenTopicCache.Read,
        private val cloudDataSource: CardsListCloudDataSource,
        private val cacheDataSource: CardsListCacheDataSource
    ) : CardsListRepository {
        override suspend fun data() = try {
            val list = mutableListOf<CardsList>()
            val topic = readTopic.read()
            val cards = cacheDataSource.cards(topic)
            if (cards.isEmpty())
                list.add(CardsList.NoCardsHint)
            else
                list.addAll(cards)
            list
        } catch (e: Exception) {
            listOf(CardsList.Error(e.message ?: "error"))
        }

        override suspend fun init(reload: ReloadWithError) = cloudDataSource.init(reload)

        override fun save(data: CardInfo) = saveCards.save(data)
    }
}