package com.bbbrrr8877.totalrecall.createCard.data

import com.bbbrrr8877.totalrecall.topics.data.ChosenTopicCache
import java.util.Calendar

interface CreateCardRepository {

    suspend fun createCard(answer: String, clue: String): CreateCardResult

    class Base(
        private val chosenTopicCache: ChosenTopicCache.Read,
        private val createCardCloudDataSource: CreateCardCloudDataSource
    ) : CreateCardRepository {
        override suspend fun createCard(answer: String, clue: String) = try {
            val topicName = chosenTopicCache.read().name()
            val topicId = chosenTopicCache.read().id()
            val date = Calendar.getInstance().timeInMillis
            createCardCloudDataSource.createCard(topicId, answer, clue, topicName, order = 0, date)
            CreateCardResult.Success
        } catch (e: Exception) {
            CreateCardResult.Failed(e.message ?: "error")
        }
    }
}