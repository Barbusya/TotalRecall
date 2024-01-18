package com.bbbrrr8877.totalrecall.createTopics.data

interface CreateTopicsRepository {

    fun create(name: String): CreateTopicResult
    fun contains(name: String): Boolean

    class Base : CreateTopicsRepository {
        override fun create(name: String): CreateTopicResult {
            TODO("Not yet implemented")
        }

        override fun contains(name: String): Boolean {
            TODO("Not yet implemented")
        }
    }
}