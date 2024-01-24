package com.bbbrrr8877.totalrecall.cardsList.data

import com.bbbrrr8877.totalrecall.cardsList.presentation.CardsInfo
import com.bbbrrr8877.totalrecall.core.ObjectStorage

interface ChosenCardsCache {

    interface Save : com.bbbrrr8877.totalrecall.core.Save<CardsInfo>

    interface Read : com.bbbrrr8877.totalrecall.core.Read<CardsInfo>

    interface Mutable : Save, Read

    class Base(
        private val objectStorage: ObjectStorage.Mutable,
        private val key: String = "ChosenCardCache"
    ) : Mutable {
        override fun read() = objectStorage.read(key, CardsInfo("", "", "",""))

        override fun save(data: CardsInfo) = objectStorage.save(key, data)
    }
}