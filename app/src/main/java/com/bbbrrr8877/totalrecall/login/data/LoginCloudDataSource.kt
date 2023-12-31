package com.bbbrrr8877.totalrecall.login.data

import com.bbbrrr8877.totalrecall.core.ProvideDatabase
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

interface LoginCloudDataSource {

    suspend fun login()

    class Base(private val provideDatabase: ProvideDatabase) : LoginCloudDataSource {

        override suspend fun login() {
            val user = Firebase.auth.currentUser
            val uid = user!!.uid
            val email = user.email!!
            val displayName = user.displayName

            if (email.isNullOrEmpty())
                throw IllegalStateException("problem occurred while getting email")

            val result = provideDatabase.database().child("user")
                .child(uid)
                .setValue(UserProfileCloud(email, displayName))
            handleResult(result)
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

private data class UserProfileCloud(
    val mail: String = "",
    val name: String? = null
)