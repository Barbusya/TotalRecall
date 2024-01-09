package com.bbbrrr8877.totalrecall.topics.presentation

import android.widget.TextView
import com.bbbrrr8877.totalrecall.R

interface TopicUi {

    fun id(): String
    fun openTopic(open: OpenTopic) = Unit
    fun orderId(): Int
    fun map(textView: TextView)

    object Progress : TopicUi {
        override fun id() = "TopicUiProgress"
        override fun orderId() = 0
        override fun map(textView: TextView) = Unit
    }

    data class Topic(private val key: String, private val name: String) : TopicUi {
        override fun id() = key
        override fun orderId() = 2
        override fun map(textView: TextView) {
            textView.text = name
        }

        override fun openTopic(open: OpenTopic) {
            open.openTopic(TopicInfo(key, name, "todo", 0))
        }
    }

    data class Error(private val message: String) : TopicUi {
        override fun id() = "TopicUiError$message"
        override fun orderId() = 7
        override fun map(textView: TextView) {
            textView.text = message
        }
    }

    object TopicTitle : TopicUi {
        override fun id() = "TopicUiTopicTitle"
        override fun orderId() = 1
        override fun map(textView: TextView) = textView.setText(R.string.your_topic)

    }

    object NoTopicsHint : TopicUi {
        override fun id() = "TopicUiNoTopics"
        override fun orderId() = 3
        override fun map(textView: TextView) = textView.setText(R.string.no_topics_hint)
    }
}

data class TopicInfo(
    private val id: String,
    private val name: String,
    private val description: String,
    private val amount: Int,
)