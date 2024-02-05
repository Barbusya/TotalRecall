package com.bbbrrr8877.totalrecall.createTopics.data

import com.bbbrrr8877.totalrecall.core.ProvideFirebaseDatabase
import com.bbbrrr8877.totalrecall.topics.data.cloud.TopicsCloud
import com.bbbrrr8877.totalrecall.topics.presentation.TopicInfo
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

interface CreateTopicsCloudDataSource {

    suspend fun createTopic(name: String): TopicInfo

    class Base(private val provideDatabase: ProvideFirebaseDatabase) : CreateTopicsCloudDataSource {

        override suspend fun createTopic(name: String): TopicInfo {
            val myUid = Firebase.auth.currentUser!!.uid
            val reference = provideDatabase.cloudDatabase().child("topics").push()
            val task = reference.setValue(TopicsCloud(name, myUid))
            handleResult(task)
            return TopicInfo(reference.key!!, name)
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