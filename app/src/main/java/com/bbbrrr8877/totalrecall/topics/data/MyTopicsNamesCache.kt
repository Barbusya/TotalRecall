package com.bbbrrr8877.totalrecall.topics.data

import com.bbbrrr8877.totalrecall.core.ObjectStorage

interface MyTopicsNamesCache {

    interface Save : com.bbbrrr8877.common.Save<List<String>>

    interface Read : com.bbbrrr8877.common.Read<List<String>>

    interface Mutable : Save, Read

    class Base(
        private val objectStorage: ObjectStorage.Mutable,
        private val key: String = "MyTopicsNamesCache"
    ) : Mutable {
        override fun read() = objectStorage.read(key, Wrapper(listOf())).list

        override fun save(data: List<String>) = objectStorage.save(key, Wrapper(data))
    }

    private data class Wrapper(val list: List<String>)
}