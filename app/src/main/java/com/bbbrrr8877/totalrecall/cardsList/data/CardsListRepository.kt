package com.bbbrrr8877.totalrecall.cardsList.data

import android.util.Log
import com.bbbrrr8877.totalrecall.cardsList.presentation.CardInfo
import com.bbbrrr8877.totalrecall.core.InitialReloadCallback
import com.bbbrrr8877.totalrecall.core.Save
import com.bbbrrr8877.totalrecall.topics.data.ChosenTopicCache
import com.bbbrrr8877.totalrecall.topics.presentation.ReloadWithError

interface CardsListRepository : InitialReloadCallback, Save<CardInfo> {

    suspend fun data(): List<CardsList>

    class Base(
        private val saveCards: ChosenCardsCache.Save,
        private val readTopic: ChosenTopicCache.Read,
        private val cloudDataSource: CardsListCloudDataSource
    ) : CardsListRepository {
        override suspend fun data() = try {
            val list = mutableListOf<CardsList>()
            val topic = readTopic.read()
            val cards = cloudDataSource.cards(topic)
            Log.d("Bulat", "Repository list: $cards")
            if (cards.isEmpty())
                list.add(CardsList.NoCardsHint)
            else
                list.addAll(cards)
            list
        } catch (e: Exception) {
            listOf(CardsList.Error(e.message ?: "error"))
        }

        override fun init(reload: ReloadWithError) = cloudDataSource.init(reload)

        override fun save(data: CardInfo) = saveCards.save(data)
    }
}