package com.bbbrrr8877.totalrecall.cardsList.data

import android.util.Log
import com.bbbrrr8877.totalrecall.cardsList.presentation.CardsInfo
import com.bbbrrr8877.totalrecall.core.InitialReloadCallback
import com.bbbrrr8877.totalrecall.core.Save
import com.bbbrrr8877.totalrecall.topics.presentation.ReloadWithError

interface CardsListRepository : InitialReloadCallback, Save<CardsInfo> {

    suspend fun data(): List<CardsList>

    class Base(
        private val saveCards: ChosenCardsCache.Save,
        private val cloudDataSource: CardsListCloudDataSource
    ) : CardsListRepository {
        override suspend fun data() = try {
            val list = mutableListOf<CardsList>()
            val cards = cloudDataSource.cards()
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

        override fun save(data: CardsInfo) = saveCards.save(data)
    }
}