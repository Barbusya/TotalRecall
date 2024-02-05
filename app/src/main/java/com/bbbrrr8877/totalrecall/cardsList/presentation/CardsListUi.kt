package com.bbbrrr8877.totalrecall.cardsList.presentation

import android.widget.TextView
import com.bbbrrr8877.totalrecall.R

interface CardsListUi {

    fun id(): String

    fun orderId(): Int

    fun map(tvAnswer: TextView, tvClue: TextView)

    object Progress : CardsListUi {
        override fun id() = "CardUiProgress"
        override fun orderId() = 0

        override fun map(tvAnswer: TextView, tvClue: TextView) = Unit

    }

    data class Card(
        private val key: String,
        private val answer: String,
        private val clue: String,
        private val topic: String = ""
    ) : CardsListUi {
        override fun id() = key
        override fun orderId() = 2

        override fun map(tvAnswer: TextView, tvClue: TextView) {
            tvAnswer.text = answer
            tvClue.text = clue
        }

    }

    data class Error(private val message: String) : CardsListUi {
        override fun id() = "CardsListUiError$message"
        override fun orderId() = 7

        override fun map(tvAnswer: TextView, tvClue: TextView) {
            tvAnswer.text = message
        }

    }

    object NoCardsHint : CardsListUi {
        override fun id() = "CardsListUiNoHint"
        override fun orderId() = 3

        override fun map(tvAnswer: TextView, tvClue: TextView) {
            tvAnswer.setText(R.string.no_cards_hint)
        }

    }

}

data class CardInfo(
    private val id: String,
    private val answer: String,
    private val clue: String,
    private val topic: String = "",
    private val owner: String
)