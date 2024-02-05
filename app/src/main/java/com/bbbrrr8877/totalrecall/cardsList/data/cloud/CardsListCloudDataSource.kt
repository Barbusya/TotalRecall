package com.bbbrrr8877.totalrecall.cardsList.data.cloud

import android.util.Log
import com.bbbrrr8877.totalrecall.cardsList.data.cache.CardCache
import com.bbbrrr8877.totalrecall.cardsList.data.cache.CardsDao
import com.bbbrrr8877.totalrecall.core.InitialReloadCallback
import com.bbbrrr8877.totalrecall.core.ProvideFirebaseDatabase
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

interface CardsListCloudDataSource : InitialReloadCallback {

    class Base(
        private val dao: CardsDao,
        private val provideFirebaseDatabase: ProvideFirebaseDatabase
    ) : CardsListCloudDataSource {

        override suspend fun init(reload: ReloadWithError) {
            val muUid = Firebase.auth.currentUser!!.uid
            val query = provideFirebaseDatabase.cloudDatabase()
                .child("cards")
                .orderByChild("owner")
                .equalTo(muUid)
            val sourceList = HandleCards(query).list()
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
            reload.reload()
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

