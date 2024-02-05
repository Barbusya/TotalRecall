package com.bbbrrr8877.totalrecall.cardsList.data

import com.bbbrrr8877.totalrecall.cardsList.presentation.CardInfo
import com.bbbrrr8877.totalrecall.core.ObjectStorage

interface ChosenCardCache {

    interface Save : com.bbbrrr8877.totalrecall.core.Save<CardInfo>

    interface Read : com.bbbrrr8877.totalrecall.core.Read<CardInfo>

    interface Mutable : Save, Read

    class Base(
        private val objectStorage: ObjectStorage.Mutable,
        private val key: String = "ChosenCardCache"
    ) : Mutable {
        override fun read() = objectStorage.read(key, CardInfo("", "", "", "", ""))

        override fun save(data: CardInfo) = objectStorage.save(key, data)
    }
}