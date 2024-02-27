package com.bbbrrr8877.totalrecall.cardsList.data

import com.bbbrrr8877.common.InitialReloadCallback
import com.bbbrrr8877.common.ReloadWithError
import com.bbbrrr8877.common.Save
import com.bbbrrr8877.totalrecall.cardsList.presentation.CardInfo
import com.bbbrrr8877.totalrecall.cardsList.presentation.SwipeListener
import com.bbbrrr8877.totalrecall.cardsList.presentation.ToolbarText
import com.bbbrrr8877.totalrecall.topics.data.ChosenTopicCache

interface CardsListRepository : InitialReloadCallback, Save<CardInfo>, SwipeListener, ToolbarText {

    suspend fun data(): List<CardsList>

    class Base(
        private val saveCards: ChosenCardCache.Save,
        private val readTopic: ChosenTopicCache.Read,
        private val cloudDataSource: CardsListCloudDataSource
    ) : CardsListRepository {
        override suspend fun data() = try {
            val list = mutableListOf<CardsList>()
            val topic = readTopic.read()
            val cards = cloudDataSource.cards(topic)
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
        override fun learned(cardInfo: CardInfo) = cloudDataSource.learned(cardInfo)
        override fun reset(cardInfo: CardInfo) = cloudDataSource.reset(cardInfo)
        override fun toolbarText() = readTopic.read().name()

    }
}