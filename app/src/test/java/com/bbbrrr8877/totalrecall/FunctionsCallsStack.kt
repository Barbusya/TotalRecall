package com.bbbrrr8877.totalrecall

import org.junit.Assert

interface FunctionsCallsStack {

    fun put(value: String)
    fun checkCalled(value: String)
    fun checkStack(value: Int)

    class Base : FunctionsCallsStack {

        private val list = mutableListOf<String>()

        override fun put(value: String) {
            list.add(value)
        }

        override fun checkCalled(value: String) {
            Assert.assertEquals(list.contains(value), true)
        }

        override fun checkStack(value: Int) {
            Assert.assertEquals(value, list.size)
        }

    }
}