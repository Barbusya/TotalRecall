package com.bbbrrr8877.totalrecall.core

import android.content.Context
import androidx.room.Room
import com.bbbrrr8877.totalrecall.cardsList.data.cache.CardsDataBase

interface ProvideLocalDatabase {

    fun roomDatabase(): CardsDataBase

    class Base(private val context: Context) : ProvideLocalDatabase {

        private val database by lazy {
            return@lazy Room.databaseBuilder(
                context,
                CardsDataBase::class.java,
                "cards_database"
            )
                .build()
        }

        override fun roomDatabase(): CardsDataBase = database
    }
}