package com.bbbrrr8877.totalrecall.topics.data

import android.util.Log
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

    suspend fun myTopics(): List<TopicList>
    suspend fun otherTopics(): List<TopicList>

    class Base(
        private val myTopicsNamesCache: MyTopicsNamesCache.Save,
        private val provideDatabase: ProvideDatabase
    ) : TopicsCloudDataSource {

        private val myTopicsCached = mutableListOf<TopicList>()
        private var loadedTopics = false
        private val otherTopicsIdsListCached = mutableListOf<String>()

        override fun init(reload: ReloadWithError) {
            val myUserId = Firebase.auth.currentUser!!.uid
            val query = provideDatabase.database()
                .child("topics-members")
                .orderByChild("memberId")
                .equalTo(myUserId)
            Log.d("Bulat", "query: $query")
            query.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val data = snapshot.children.mapNotNull {
                        it.getValue(OtherTopicCloud::class.java)?.topicId
                    }
                    otherTopicsIdsListCached.clear()
                    otherTopicsIdsListCached.addAll(data)
                    reload.reload()
                }

                override fun onCancelled(error: DatabaseError) = reload.error(error.message)
            })

        }

        override suspend fun myTopics(): List<TopicList> {
            if (!loadedTopics) {
                val myUserId = Firebase.auth.currentUser!!.uid
                val query = provideDatabase.database()
                    .child("topics")
                    .orderByChild("owner")
                    .equalTo(myUserId)
                val sourceList = HandleTopics(query).list()
                val list = sourceList.map { (id, topicsCloud) ->
                    TopicList.MyTopicsList(
                        id,
                        topicsCloud.name
                    )
                }
                myTopicsCached.addAll(list)
                val myTopicsNameList = sourceList.map { (_, topicCloud) -> topicCloud.name }
                myTopicsNamesCache.save(myTopicsNameList)
                loadedTopics = true
                Log.d("Bulat", "topicCached: $myTopicsCached")
            }
            return myTopicsCached
        }

        override suspend fun otherTopics(): List<TopicList> {
            val list = mutableListOf<TopicList>()
            otherTopicsIdsListCached.forEach { topicId ->
                val query = provideDatabase.database()
                    .child("topics")
                    .orderByKey()
                    .equalTo(topicId)

                val topics =
                    HandleTopics(query).list().map { (id, topicCloud) ->
                        TopicList.OtherTopicList(
                            id,
                            topicCloud.name,
                            topicCloud.owner
                        )
                    }
                list.addAll(topics)
            }
            return list
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

data class TopicsCloud(
    val owner: String = "",
    val name: String = "",
)

private data class OtherTopicCloud(
    val memberId: String = "",
    val topicId: String = "",
)


