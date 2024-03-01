package com.bbbrrr8877.totalrecall.library.presentation

import android.view.View
import android.widget.TextView
import com.bbbrrr8877.totalrecall.R

interface LibraryUi {

    fun id(): String
    fun orderId(): Int
    fun load(loadList: LoadList) = Unit
    fun map(tvName: TextView, tvDescription: TextView, button: View)

    object Progress : LibraryUi {
        override fun id() = "LibraryUiProgress"
        override fun orderId() = 0
        override fun map(tvName: TextView, tvDescription: TextView, button: View) = Unit
    }

    data class LibraryTopic(
        private val key: String,
        private val name: String,
        private val description: String,
    ) : LibraryUi {
        override fun id() = key
        override fun orderId() = 2
        override fun map(tvName: TextView, tvDescription: TextView, button: View) {
            tvName.text = name
            tvDescription.text = description
        }

        override fun load(loadList: LoadList) {
            loadList.load(LibraryItemInfo(key, name))
        }
    }

    data class Error(private val message: String) : LibraryUi {
        override fun id() = "LibraryUiError$message"
        override fun orderId() = 7
        override fun map(tvName: TextView, tvDescription: TextView, button: View) {
            tvName.text = message
            button.visibility = View.GONE
        }
    }

    object NoLibraryHint : LibraryUi {
        override fun id() = "LibraryUiNoTopics"
        override fun orderId() = 3
        override fun map(tvName: TextView, tvDescription: TextView, button: View) {
            tvName.setText(R.string.no_libraries_hint)
            button.visibility = View.GONE
        }

    }
}

data class LibraryItemInfo(
    private val id: String,
    private val name: String,
)