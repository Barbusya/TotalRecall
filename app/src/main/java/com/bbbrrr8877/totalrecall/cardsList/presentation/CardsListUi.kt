package com.bbbrrr8877.totalrecall.cardsList.presentation

interface CardsListUi {

    object Progress : CardsListUi {

    }

    data class Card(
        private val key: String,
        private val answer: String,
        private val clue: String,
        private val image: String = ""
    ) : CardsListUi {

    }

    data class Error(private val message: String) : CardsListUi {

    }


}