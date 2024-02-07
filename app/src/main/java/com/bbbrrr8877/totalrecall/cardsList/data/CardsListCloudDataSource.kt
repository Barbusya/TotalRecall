package com.bbbrrr8877.totalrecall.cardsList.data

import android.util.Log
import com.bbbrrr8877.totalrecall.core.InitialReloadCallback
import com.bbbrrr8877.totalrecall.core.ProvideDatabase
import com.bbbrrr8877.totalrecall.topics.presentation.ReloadWithError
import com.bbbrrr8877.totalrecall.topics.presentation.TopicInfo
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

interface CardsListCloudDataSource : InitialReloadCallback {

    suspend fun cards(topicInfo: TopicInfo): List<CardsList>

    class Base(
        private val provideDatabase: ProvideDatabase
    ) : CardsListCloudDataSource {

        private val cardsList = mutableListOf<CardsList>()
        private var loadedCards = false

        override fun init(reload: ReloadWithError) {
            reload.reload()
        }

        override suspend fun cards(topicInfo: TopicInfo): List<CardsList> {
            if (!loadedCards) {
                val myUserId = Firebase.auth.currentUser!!.uid
                val topicName = topicInfo.name()
                val topicId = topicInfo.id()
                val query = provideDatabase.database()
                    .child(myUserId)
                    .child(topicId)
                    .orderByChild("topic")
                    .equalTo(topicName)
                val sourceList = HandleCards(query).list()
                val list = sourceList.map { (id, card) ->
                    CardsList.MyCardsList(
                        key = id,
                        answer = card.answer,
                        clue = card.clue,
                        topicName = card.topic
                    )
                }
                cardsList.addAll(list)
                loadedCards = true
            }
            return cardsList
        }
    }
}

private class HandleCards(private val query: Query) {

    suspend fun list(): List<Pair<String, CardCloud>> = suspendCoroutine { cont ->
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.children.mapNotNull {
                    Log.d("Bulat", "Cards data $it")
                    Pair(
                        it.key!!,
                        it.getValue(CardCloud::class.java)!!
                    )
                }
                cont.resume(data)
            }

            override fun onCancelled(error: DatabaseError) {
                cont.resumeWithException(IllegalStateException(error.message))
            }
        })
    }
}

data class CardCloud(
    val answer: String = "",
    val clue: String = "",
    val topic: String = "",
    val order: Int = 0,
    val date: Long = 0,
)

