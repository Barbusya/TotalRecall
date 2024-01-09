package com.bbbrrr8877.totalrecall.topics.data

import com.bbbrrr8877.totalrecall.core.ProvideDatabase
import com.bbbrrr8877.totalrecall.topics.presentation.ReloadWithError
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


interface TopicsCloudDataSource : InitialReloadCallback {

    suspend fun topicsList(): List<Topic>

    class Base(
        private val provideDatabase: ProvideDatabase
    ) : TopicsCloudDataSource {

        private val topicsCached = mutableListOf<Topic>()
        private var loadedTopics = false
        //todo perhaps we don't use this
        private val topicsIdCached = mutableListOf<String>()

        override fun init(reload: ReloadWithError) {
            val myUserId = Firebase.auth.currentUser!!.uid
            val query = provideDatabase.database()
                .equalTo(myUserId)
            query.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val data = snapshot.children.mapNotNull {
                        it.getValue(TopicsCloud::class.java)!!.topicId
                    }
                    topicsIdCached.clear()
                    topicsIdCached.addAll(data)
                    reload.reload()
                }

                override fun onCancelled(error: DatabaseError) = reload.error(error.message)
            })

        }


        override suspend fun topicsList(): List<Topic> {
            if (!loadedTopics) {
                val myUserId = Firebase.auth.currentUser!!.uid
                val query = provideDatabase.database()
                    .child("topics")
                    .orderByChild("owner")
                    .equalTo(myUserId)
                val list = HandleTopics(query).list()
                    .map { (id, topicsCloud) -> Topic.MyTopics(id, topicsCloud.topicName) }
                topicsCached.addAll(list)
                loadedTopics = true
            }
            return topicsCached
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
                cont.resume(data.map { Pair(it.first, it.second) })
            }

            override fun onCancelled(error: DatabaseError) {
                cont.resumeWithException(IllegalStateException(error.message))
            }
        })
    }
}

private data class TopicsCloud(
    val topicId: String = "",
    val topicName: String = "",
    val count: Int = 0
)


