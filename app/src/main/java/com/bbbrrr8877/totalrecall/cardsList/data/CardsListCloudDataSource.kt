package com.bbbrrr8877.totalrecall.cardsList.data

import com.bbbrrr8877.totalrecall.cardsList.presentation.CardInfo
import com.bbbrrr8877.totalrecall.cardsList.presentation.SwipeListener
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

interface CardsListCloudDataSource : InitialReloadCallback, SwipeListener {

    suspend fun cards(topicInfo: TopicInfo): List<CardsList>

    class Base(
        private val provideDatabase: ProvideDatabase,
        private val learningOrder: LearningOrder,
    ) : CardsListCloudDataSource {


        override fun init(reload: ReloadWithError) {
            reload.reload()
        }

        override fun learned(cardInfo: CardInfo) {
            val myUserId = Firebase.auth.currentUser!!.uid
            var order = cardInfo.order()
            provideDatabase.database()
                .child(myUserId)
                .child(cardInfo.topicId())
                .child(cardInfo.id())
                .child("order")
                .setValue(++order)
            provideDatabase.database()
                .child(myUserId)
                .child(cardInfo.topicId())
                .child(cardInfo.id())
                .child("date")
                .setValue(cardInfo.date())
        }

        override fun reset(cardInfo: CardInfo) {
            val myUserId = Firebase.auth.currentUser!!.uid
            val resetTime = cardInfo.date() + MIN_10
            provideDatabase.database()
                .child(myUserId)
                .child(cardInfo.topicId())
                .child(cardInfo.id())
                .child("order")
                .setValue(0)
            provideDatabase.database()
                .child(myUserId)
                .child(cardInfo.topicId())
                .child(cardInfo.id())
                .child("date")
                .setValue(resetTime)
        }

        override suspend fun cards(topicInfo: TopicInfo): List<CardsList> {
            val myUserId = Firebase.auth.currentUser!!.uid
            val topicId = topicInfo.id()
            val query = provideDatabase.database()
                .child(myUserId)
                .child(topicId)
                .orderByChild("topicId")
                .equalTo(topicId)
            val sourceList = HandleCards(query).list()
            val filteredList = sourceList.filter { (_, card) ->
                learningOrder.show(
                    card.order,
                    card.date
                )
            }
            val list = filteredList.map { (id, card) ->
                CardsList.MyCardsList(
                    key = id,
                    answer = card.answer,
                    clue = card.clue,
                    topicName = card.topic,
                    order = card.order,
                    date = card.date,
                    topicId = card.topicId
                )
            }
            return list
        }
    }

    companion object {
        private const val MIN_10 = 600_000
    }
}

private class HandleCards(private val query: Query) {

    suspend fun list(): List<Pair<String, CardCloud>> = suspendCoroutine { cont ->
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.children.mapNotNull {
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
    val topicId: String = "",
)

