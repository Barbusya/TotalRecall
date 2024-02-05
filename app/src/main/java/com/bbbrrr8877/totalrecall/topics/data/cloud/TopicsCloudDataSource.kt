package com.bbbrrr8877.totalrecall.topics.data.cloud

import com.bbbrrr8877.totalrecall.cardsList.data.cache.CardsDao
import com.bbbrrr8877.totalrecall.core.InitialReloadCallback
import com.bbbrrr8877.totalrecall.core.ProvideFirebaseDatabase
import com.bbbrrr8877.totalrecall.topics.data.MyTopicsNamesCache
import com.bbbrrr8877.totalrecall.topics.data.cache.TopicCache
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

    class Base(
        private val dao: CardsDao,
        private val myTopicsNamesCache: MyTopicsNamesCache.Save,
        private val provideDatabase: ProvideFirebaseDatabase
    ) : TopicsCloudDataSource {

        private var loadedTopics = false

        override suspend fun init(reload: ReloadWithError) {
            val myUserId = Firebase.auth.currentUser!!.uid
            val query = provideDatabase.cloudDatabase()
                .child("topics")
                .orderByChild("owner")
                .equalTo(myUserId)
            val sourceList = HandleTopics(query).list()
            sourceList.map { (id, topicsCloud) ->
                dao.insertTopic(
                    TopicCache(
                        id = id,
                        name = topicsCloud.name,
                        owner = topicsCloud.owner
                    )
                )
            }
            val myTopicsNameList = sourceList.map { (_, topicCloud) -> topicCloud.name }
            myTopicsNamesCache.save(myTopicsNameList)
            loadedTopics = true
            reload.reload()

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
