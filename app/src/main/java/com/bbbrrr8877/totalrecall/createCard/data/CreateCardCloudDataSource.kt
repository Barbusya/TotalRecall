package com.bbbrrr8877.totalrecall.createCard.data

import com.bbbrrr8877.totalrecall.cardsList.data.CardCloud
import com.bbbrrr8877.totalrecall.cardsList.presentation.CardInfo
import com.bbbrrr8877.totalrecall.core.ProvideDatabase
import com.google.android.gms.tasks.Task
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

interface CreateCardCloudDataSource {

    suspend fun createCard(answer: String, clue: String, topic: String): CardInfo

    class Base(private val provideDatabase: ProvideDatabase) : CreateCardCloudDataSource {
        override suspend fun createCard(answer: String, clue: String, topic: String): CardInfo {
            val reference = provideDatabase.database().child("cards").push()
            val task = reference.setValue(CardCloud(answer, clue, topic))
            handleResult(task)
            return CardInfo(reference.key!!, answer, clue, topic)
        }

        private suspend fun handleResult(value: Task<Void>): Unit =
            suspendCoroutine { continuation ->
                value.addOnSuccessListener {
                    continuation.resume(Unit)
                }.addOnFailureListener {
                    continuation.resumeWithException(it)
                }
            }
    }
}