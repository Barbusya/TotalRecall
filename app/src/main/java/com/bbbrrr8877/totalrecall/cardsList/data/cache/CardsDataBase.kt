package com.bbbrrr8877.totalrecall.cardsList.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bbbrrr8877.totalrecall.topics.data.cache.TopicCache

@Database(entities = [CardCache::class, TopicCache::class], version = 1)
abstract class CardsDataBase : RoomDatabase() {

    abstract fun cardsDao(): CardsDao
}