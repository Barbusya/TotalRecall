package com.bbbrrr8877.totalrecall.core

import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

interface ProvideFirebaseDatabase {

    fun cloudDatabase(): DatabaseReference

    class Base : ProvideFirebaseDatabase {

        init {
            Firebase.database(DATABASE_URL).setPersistenceEnabled(false)
        }

        override fun cloudDatabase(): DatabaseReference {
            return Firebase.database(DATABASE_URL).reference.root
        }

        companion object {
            private const val DATABASE_URL =
                "https://totalrecall-31235-default-rtdb.europe-west1.firebasedatabase.app"
        }
    }
}