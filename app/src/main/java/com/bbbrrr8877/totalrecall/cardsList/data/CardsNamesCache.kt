package com.bbbrrr8877.totalrecall.cardsList.data

import com.bbbrrr8877.totalrecall.core.ObjectStorage

interface CardsNamesCache {

    interface Save : com.bbbrrr8877.totalrecall.core.Save<List<String>>

    interface Read : com.bbbrrr8877.totalrecall.core.Read<List<String>>

    interface Mutable : Save, Read

    class Base(
        private val objectStorage: ObjectStorage.Mutable,
        private val key: String = "CardsNamesCache"
    ) : Mutable {
        override fun read(): List<String> = objectStorage.read(key, Wrapper(listOf())).list

        override fun save(data: List<String>) = objectStorage.save(key, data)
    }

    private data class Wrapper(val list: List<String>)
}