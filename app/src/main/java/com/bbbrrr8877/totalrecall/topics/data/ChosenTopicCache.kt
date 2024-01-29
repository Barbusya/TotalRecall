package com.bbbrrr8877.totalrecall.topics.data

import com.bbbrrr8877.totalrecall.core.ObjectStorage
import com.bbbrrr8877.totalrecall.topics.presentation.TopicInfo

interface ChosenTopicCache {
    interface Save : com.bbbrrr8877.totalrecall.core.Save<TopicInfo>
    interface Read : com.bbbrrr8877.totalrecall.core.Read<TopicInfo>
    interface Mutable : Save, Read

    class Base(
        private val objectStorage: ObjectStorage.Mutable,
        private val key: String = "ChosenTopicCache"
    ) : Mutable {
        override fun read(): TopicInfo = objectStorage.read(key, TopicInfo("", ""))


        override fun save(data: TopicInfo) = objectStorage.save(key, data)
    }
}