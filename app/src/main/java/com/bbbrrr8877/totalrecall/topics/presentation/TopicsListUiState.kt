package com.bbbrrr8877.totalrecall.topics.presentation

import com.bbbrrr8877.common.Mapper
import com.bbbrrr8877.totalrecall.topics.data.TopicList

interface TopicsListUiState {
    fun show(mapper: Mapper.Unit<List<TopicListUi>>)

    abstract class Abstract(private val list: List<TopicListUi>) : TopicsListUiState {
        override fun show(mapper: Mapper.Unit<List<TopicListUi>>) = mapper.map(list)
    }

    data class Base(private val list: List<TopicList>) : Abstract(list.map {it.toUi()})

    abstract class Single(topicUi: TopicListUi) : Abstract(listOf(topicUi))

    object Progress : Single(TopicListUi.Progress)
    class Error(message: String) : Single(TopicListUi.Error(message))
}