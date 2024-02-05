package com.bbbrrr8877.totalrecall.cardsList.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bbbrrr8877.totalrecall.topics.data.cache.TopicCache

@Dao
interface CardsDao {

    @Query("SELECT * FROM cards_table")
    fun allCards(): List<CardCache>

    @Query("SELECT * FROM topics_table")
    fun allTopics(): List<TopicCache>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCard(card: CardCache)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTopic(topic: TopicCache)

    @Query("SELECT * FROM cards_table WHERE id = :id")
    fun card(id: String): CardCache?

    @Query("SELECT * FROM topics_table WHERE id = :id")
    fun topic(id: String): TopicCache
}