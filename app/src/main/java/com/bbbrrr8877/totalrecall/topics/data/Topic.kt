package com.bbbrrr8877.totalrecall.topics.data

import com.bbbrrr8877.totalrecall.topics.presentation.TopicUi

interface Topic {

    fun toUi(): TopicUi

    object TopicsTitle : Topic {
        override fun toUi() = TopicUi.TopicTitle
    }

    data class OtherTopic(
        private val key: String,
        private val name: String,
        private val owner: String,
    ) : Topic {
        override fun toUi() = TopicUi.OtherTopic(key, name, owner)
    }

    data class MyTopics(private val key: String, private val name: String): Topic {
        override fun toUi() = TopicUi.MyTopic(key, name)
    }

    object NoTopicsHint : Topic {
        override fun toUi() = TopicUi.NoTopicsHint
    }

    data class Error(private val message: String) : Topic {
        override fun toUi() = TopicUi.Error(message)
    }
}