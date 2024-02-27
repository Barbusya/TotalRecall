package com.bbbrrr8877.android

import android.content.Context

interface ManageResource {

    fun string(id: Int): String

    class Base(private val context: Context) : ManageResource {
        override fun string(id: Int) = context.getString(id)
    }
}