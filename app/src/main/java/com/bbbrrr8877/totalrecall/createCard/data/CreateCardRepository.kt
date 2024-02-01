package com.bbbrrr8877.totalrecall.createCard.data

interface CreateCardRepository {

    suspend fun createCard(answer: String, clue: String): CreateCardResult

    class Base(
        private val createCardCloudDataSource: CreateCardCloudDataSource
    ) : CreateCardRepository {
        override suspend fun createCard(answer: String, clue: String) = try {
            createCardCloudDataSource.createCard(answer, clue)
            CreateCardResult.Success
        } catch (e: Exception) {
            CreateCardResult.Failed(e.message ?: "error")
        }
    }
}