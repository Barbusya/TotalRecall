package com.bbbrrr8877.totalrecall.topics.data.cache

import com.bbbrrr8877.totalrecall.topics.data.TopicList

class TopicCacheToList : TopicCache.Mapper<TopicList.MyTopicsList> {
    override fun map(id: String, name: String, owner: String) =
        TopicList.MyTopicsList(id, name, owner)
}