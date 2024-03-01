package com.bbbrrr8877.totalrecall.topics.data

import com.bbbrrr8877.totalrecall.topics.presentation.TopicListUi

interface TopicList {

    fun toUi(): TopicListUi

    data class MyTopicsList(private val key: String, private val name: String): TopicList {
        override fun toUi() = TopicListUi.MyTopic(key, name)
    }

    object NoTopicsHint : TopicList {
        override fun toUi() = TopicListUi.NoTopicsHint
    }

    data class Error(private val message: String) : TopicList {
        override fun toUi() = TopicListUi.Error(message)
    }

}