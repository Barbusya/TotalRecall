package com.bbbrrr8877.totalrecall.cardsList.presentation

import com.bbbrrr8877.totalrecall.cardsList.data.CardsList
import com.bbbrrr8877.totalrecall.core.Mapper

interface CardsListUiState {

    fun show(mapper: Mapper.Unit<List<CardsListUi>>)

    abstract class Abstract(private val list: List<CardsListUi>) : CardsListUiState {

        override fun show(mapper: Mapper.Unit<List<CardsListUi>>) = mapper.map(list)
    }

    data class Base(private val list: List<CardsList>) : Abstract(list.map { it.toUi() })

    abstract class Single(cardsUi: CardsListUi) : Abstract(listOf(cardsUi))

    object Progress : Single(CardsListUi.Progress)

    class Error(message: String) : Single(CardsListUi.Error(message))
}