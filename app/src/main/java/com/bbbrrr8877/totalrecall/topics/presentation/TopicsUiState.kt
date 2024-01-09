package com.bbbrrr8877.totalrecall.topics.presentation

import com.bbbrrr8877.totalrecall.core.Mapper
import com.bbbrrr8877.totalrecall.topics.data.Topic

interface TopicsUiState {
    fun show(mapper: Mapper.Unit<List<TopicUi>>)

    abstract class Abstract(private val list: List<TopicUi>) : TopicsUiState {
        override fun show(mapper: Mapper.Unit<List<TopicUi>>) = mapper.map(list)
    }

    data class Base(private val list: List<Topic>) : Abstract(list.map {it.toUi()})

    abstract class Single(topicUi: TopicUi) : Abstract(listOf(topicUi))

    object Progress : Single(TopicUi.Progress)
    class Error(message: String) : Single(TopicUi.Error(message))
}