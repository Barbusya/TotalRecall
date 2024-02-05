package com.bbbrrr8877.totalrecall.topics.data.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "topics_table")
data class TopicCache(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "owner") val owner: String
) {
    interface Mapper<T> {
        fun map(id: String, name: String, owner: String): T
    }

    fun <T> map(mapper: Mapper<T>): T = mapper.map(id, name, owner)
}
