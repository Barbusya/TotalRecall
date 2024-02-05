package com.bbbrrr8877.totalrecall.cardsList.data.cache

import com.bbbrrr8877.totalrecall.cardsList.data.CardsList

class CardsCacheToList : CardCache.Mapper<CardsList.MyCardsList> {
    override fun map(id: String, answer: String, clue: String, topic: String, owner: String) =
        CardsList.MyCardsList(id, answer, clue, topic, owner)
}