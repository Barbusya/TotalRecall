package com.bbbrrr8877.totalrecall.library.data

import com.bbbrrr8877.totalrecall.library.presentation.LibraryUi


interface LibraryList {

    fun toUi(): LibraryUi

    data class MyLibraryList(
        private val key: String,
        private val name: String,
        private val description: String,
    ) : LibraryList {
        override fun toUi() = LibraryUi.LibraryTopic(key, name, description)
    }

    object NoLibraryHint : LibraryList {
        override fun toUi() = LibraryUi.NoLibraryHint
    }

    data class Error(private val message: String) : LibraryList {
        override fun toUi() = LibraryUi.Error(message)
    }
}