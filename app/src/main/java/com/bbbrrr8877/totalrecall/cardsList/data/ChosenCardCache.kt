package com.bbbrrr8877.totalrecall.cardsList.data

import com.bbbrrr8877.android.ObjectStorage
import com.bbbrrr8877.totalrecall.cardsList.presentation.CardInfo

interface ChosenCardCache {

    interface Save : com.bbbrrr8877.common.Save<CardInfo>

    interface Read : com.bbbrrr8877.common.Read<CardInfo>

    interface Mutable : Save, Read

    class Base(
        private val objectStorage: ObjectStorage.Mutable,
        private val key: String = "ChosenCardCache"
    ) : Mutable {
        override fun read() = objectStorage.read(key, CardInfo("", "", "", "", 0, 0))

        override fun save(data: CardInfo) = objectStorage.save(key, data)
    }
}