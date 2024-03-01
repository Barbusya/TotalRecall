package com.bbbrrr8877.totalrecall.library.data

import com.bbbrrr8877.common.InitialReloadCallback
import com.bbbrrr8877.common.ReloadWithError
import com.bbbrrr8877.totalrecall.core.ProvideDatabase
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

interface LibraryCloudDataSource : InitialReloadCallback {

    suspend fun library(): List<LibraryList>

    class Base(
        private val provideDatabase: ProvideDatabase
    ) : LibraryCloudDataSource {
        override fun init(reload: ReloadWithError) = reload.reload()

        override suspend fun library(): List<LibraryList> {
            val myUserId = Firebase.auth.currentUser!!.uid
            val query = provideDatabase.database()
                .child("library")
                .orderByChild("enabled")
                .equalTo(true)
            val sourceList = HandleLibrary(query).list()
            val list = sourceList.map { (id, libraryCloud) ->
                LibraryList.MyLibraryList(
                    id,
                    libraryCloud.name,
                    libraryCloud.description
                )
            }
            return list
        }
    }
}

private class HandleLibrary(private val query: Query) {

    suspend fun list(): List<Pair<String, LibraryCloud>> = suspendCoroutine { cont ->
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.children.mapNotNull {
                    Pair(
                        it.key!!,
                        it.getValue(LibraryCloud::class.java)!!
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

data class LibraryCloud(
    val name: String = "",
    val enabled: Boolean = false,
    val description: String = "",
)