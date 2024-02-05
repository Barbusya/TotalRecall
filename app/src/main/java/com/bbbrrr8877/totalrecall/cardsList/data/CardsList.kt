package com.bbbrrr8877.totalrecall.cardsList.data

import com.bbbrrr8877.totalrecall.cardsList.presentation.CardsListUi

interface CardsList {

    fun toUi(): CardsListUi

    data class MyCardsList(
        private val key: String,
        private val answer: String,
        private val clue: String,
        private val topicName: String = "",
        private val owner: String = ""
    ) : CardsList {
        override fun toUi() = CardsListUi.Card(key, answer, clue, topicName)
    }

    object NoCardsHint : CardsList {
        override fun toUi() = CardsListUi.NoCardsHint
    }

    data class Error(private val message: String) : CardsList {
        override fun toUi() = CardsListUi.Error(message)
    }

}