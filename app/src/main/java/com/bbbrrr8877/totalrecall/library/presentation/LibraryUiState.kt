package com.bbbrrr8877.totalrecall.library.presentation

import com.bbbrrr8877.common.Mapper
import com.bbbrrr8877.totalrecall.library.data.LibraryList

interface LibraryUiState {
    fun show(mapper: Mapper.Unit<List<LibraryUi>>)

    abstract class Abstract(private val list: List<LibraryUi>) : LibraryUiState {
        override fun show(mapper: Mapper.Unit<List<LibraryUi>>) = mapper.map(list)
    }

    data class Base(private val list: List<LibraryList>) : Abstract(list.map { it.toUi() })

    abstract class Single(topicUi: LibraryUi) : Abstract(listOf(topicUi))

    object Progress : Single(LibraryUi.Progress)
    class Error(message: String) : Single(LibraryUi.Error(message))
}