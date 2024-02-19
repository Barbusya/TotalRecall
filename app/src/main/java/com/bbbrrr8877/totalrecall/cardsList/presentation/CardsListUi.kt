package com.bbbrrr8877.totalrecall.cardsList.presentation

import android.view.View
import android.widget.TextView
import com.bbbrrr8877.totalrecall.R
import java.util.Calendar

interface CardsListUi {

    fun id(): String
    fun showAnswer(tvAnswer: TextView) = Unit
    fun learnedCard(learnedCard: LearnedCard) = Unit
    fun resetCard(resetCard: ResetCard) = Unit

    fun orderId(): Int

    fun map(tvAnswer: TextView, tvClue: TextView, parentView: View)

    object Progress : CardsListUi {
        override fun id() = "CardUiProgress"
        override fun orderId() = 0

        override fun map(tvAnswer: TextView, tvClue: TextView, parentView: View) = Unit

    }

    data class Card(
        private val key: String,
        private val answer: String,
        private val clue: String,
        private val topic: String = "",
        private val learnOrder: Int = 0,
        private val date: Long = 0,
        private val topicId: String = "",
    ) : CardsListUi {
        private var isShown = false

        override fun id() = key
        override fun orderId() = 2

        override fun learnedCard(learnedCard: LearnedCard) {
            learnedCard.learned(CardInfo(key, answer, clue, topic, learnOrder, date, topicId))
        }

        override fun resetCard(resetCard: ResetCard) {
            resetCard.reset(CardInfo(key, answer, clue, topic, learnOrder, date, topicId))
        }

        override fun map(tvAnswer: TextView, tvClue: TextView, parentView: View) {
            tvAnswer.text = answer
            tvAnswer.animate().apply {
                alpha(0f)
                duration = 10
            }
            tvClue.text = clue
        }

        override fun showAnswer(tvAnswer: TextView) {
            if (!isShown) {
                tvAnswer.animate().apply {
                    alpha(1f)
                    duration = 1000
                }
                isShown = true
            } else {
                tvAnswer.animate().apply {
                    alpha(0f)
                    duration = 1000
                }
                isShown = false
            }

        }

    }

    data class Error(private val message: String) : CardsListUi {
        override fun id() = "CardsListUiError$message"
        override fun orderId() = 7

        override fun map(tvAnswer: TextView, tvClue: TextView, parentView: View) {
            tvAnswer.text = message
        }

    }

    object NoCardsHint : CardsListUi {
        override fun id() = "CardsListUiNoHint"
        override fun orderId() = 3

        override fun map(tvAnswer: TextView, tvClue: TextView, parentView: View) {
            tvClue.setText(R.string.no_cards_hint)
        }

    }

}

data class CardInfo(
    private val id: String,
    private val answer: String,
    private val clue: String,
    private val topic: String = "",
    private val order: Int = 0,
    private val date: Long,
    private val topicId: String = "",
) {
    fun topicId() = topicId
    fun id() = id
    fun order() = order
    fun date() = Calendar.getInstance().timeInMillis
}
