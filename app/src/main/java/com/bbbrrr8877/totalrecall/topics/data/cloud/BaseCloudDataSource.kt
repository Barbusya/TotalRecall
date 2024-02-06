package com.bbbrrr8877.totalrecall.topics.data.cloud

import com.bbbrrr8877.totalrecall.cardsList.data.cache.CardCache
import com.bbbrrr8877.totalrecall.cardsList.data.cache.CardsDao
import com.bbbrrr8877.totalrecall.cardsList.data.cloud.CardCloud
import com.bbbrrr8877.totalrecall.core.ProvideFirebaseDatabase
import com.bbbrrr8877.totalrecall.topics.data.cache.TopicCache
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

interface BaseCloudDataSource {

    suspend fun init(firstRun: Boolean)

    class Base(
        private val dao: CardsDao,
        private val provideDatabase: ProvideFirebaseDatabase
    ) : BaseCloudDataSource {


        override suspend fun init(firstRun: Boolean) {
            if (firstRun) {
                val myUserId = Firebase.auth.currentUser!!.uid
                val cloudDatabase = provideDatabase.cloudDatabase()
                val queryTopics = cloudDatabase
                    .child("topics")
                    .orderByChild("owner")
                    .equalTo(myUserId)
                val sourceListList = HandleTopics(queryTopics).list()
                sourceListList.map { (id, topicsCloud) ->
                    dao.insertTopic(
                        TopicCache(
                            id = id,
                            name = topicsCloud.name,
                            owner = topicsCloud.owner
                        )
                    )
                }
                val queryCards = cloudDatabase
                    .child("cards")
                    .orderByChild("owner")
                    .equalTo(myUserId)
                val sourceList = HandleCards(queryCards).list()
                sourceList.map { (id, cardCloud) ->
                    dao.insertCard(
                        CardCache(
                            id = id,
                            answer = cardCloud.answer,
                            clue = cardCloud.clue,
                            topic = cardCloud.topic,
                            owner = cardCloud.owner
                        )
                    )
                }
            }
        }
    }
}

private class HandleTopics(private val query: Query) {

    suspend fun list(): List<Pair<String, TopicsCloud>> = suspendCoroutine { cont ->
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.children.mapNotNull {
                    Pair(
                        it.key!!,
                        it.getValue(TopicsCloud::class.java)!!
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