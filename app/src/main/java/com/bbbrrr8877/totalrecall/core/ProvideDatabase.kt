package com.bbbrrr8877.totalrecall.core

import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

interface ProvideDatabase {

    fun database(): DatabaseReference

    class Base : ProvideDatabase {

        init {
            Firebase.database(DATABASE_URL).setPersistenceEnabled(true)
        }

        override fun database(): DatabaseReference {
            return Firebase.database(DATABASE_URL).reference.root
        }

        companion object {
            private const val DATABASE_URL =
                "https://totalrecall-31235-default-rtdb.europe-west1.firebasedatabase.app"
        }
    }
}