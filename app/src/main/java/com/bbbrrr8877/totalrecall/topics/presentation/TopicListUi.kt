package com.bbbrrr8877.totalrecall.topics.presentation

import android.widget.TextView
import com.bbbrrr8877.totalrecall.R

interface TopicListUi {

    fun id(): String
    fun openTopic(open: OpenTopic) = Unit
    fun orderId(): Int
    fun map(textView: TextView)

    object Progress : TopicListUi {
        override fun id() = "TopicUiProgress"
        override fun orderId() = 0
        override fun map(textView: TextView) = Unit
    }

    data class MyTopic(private val key: String, private val name: String) : TopicListUi {
        override fun id() = key
        override fun orderId() = 2
        override fun map(textView: TextView) {
            textView.text = name
        }

        override fun openTopic(open: OpenTopic) {
            open.openTopic(TopicInfo(key, name, true))
        }
    }

    data class OtherTopicList(
        private val key: String,
        private val name: String,
        private val owner: String
    ) : TopicListUi {
        override fun id() = key

        override fun orderId() = 5

        override fun map(textView: TextView) {
            textView.text = name
        }

        override fun openTopic(open: OpenTopic) =
            open.openTopic(TopicInfo(key, name, false, owner))
    }

    data class Error(private val message: String) : TopicListUi {
        override fun id() = "TopicUiError$message"
        override fun orderId() = 7
        override fun map(textView: TextView) {
            textView.text = message
        }
    }

    object TopicTitle : TopicListUi {
        override fun id() = "TopicUiTopicTitle"
        override fun orderId() = 1
        override fun map(textView: TextView) = textView.setText(R.string.your_topic)

    }

    object NoTopicsHint : TopicListUi {
        override fun id() = "TopicUiNoTopics"
        override fun orderId() = 3
        override fun map(textView: TextView) = textView.setText(R.string.no_topics_hint)
    }
}

data class TopicInfo(
    private val id: String,
    private val name: String,
    private val isMyTopic: Boolean,
    private val ownerId: String = "",
)