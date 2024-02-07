package com.bbbrrr8877.totalrecall.topics.data

import android.util.Log
import com.bbbrrr8877.totalrecall.core.InitialReloadCallback
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

    suspend fun topics(): List<TopicList>

    class Base(
        private val myTopicsNamesCache: MyTopicsNamesCache.Save,
        private val provideDatabase: ProvideDatabase
    ) : TopicsCloudDataSource {

        private val myTopicsCached = mutableListOf<TopicList>()
        private var loadedTopics = false

        override fun init(reload: ReloadWithError) {
            reload.reload()
        }

        override suspend fun topics(): List<TopicList> {
            if (!loadedTopics) {
                val myUserId = Firebase.auth.currentUser!!.uid
                val query = provideDatabase.database()
                    .child(myUserId)
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
            }
            return myTopicsCached
        }
    }
}

private class HandleTopics(private val query: Query) {

    suspend fun list(): List<Pair<String, TopicsCloud>> = suspendCoroutine { cont ->
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.children.mapNotNull {
                    Log.d("Bulat", "Topics data: $it")
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

data class TopicsCloud(
    val name: String = "",
    val owner: String = ""
)
