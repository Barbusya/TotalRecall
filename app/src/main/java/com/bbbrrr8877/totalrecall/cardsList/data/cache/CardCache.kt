package com.bbbrrr8877.totalrecall.cardsList.data.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cards_table")
data class CardCache(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "answer") val answer: String,
    @ColumnInfo(name = "clue") val clue: String,
    @ColumnInfo(name = "topic") val topic: String,
    @ColumnInfo(name = "owner") val owner: String
) {
    interface Mapper<T> {
        fun map(id: String, answer: String, clue: String, topic: String, owner: String): T
    }

    fun <T> map(mapper: Mapper<T>): T = mapper.map(id, answer, clue, topic, owner)
}
