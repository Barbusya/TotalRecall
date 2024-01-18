package com.bbbrrr8877.totalrecall

import com.bbbrrr8877.totalrecall.main.NavigationCommunication
import com.bbbrrr8877.totalrecall.main.Screen
import org.junit.Assert

interface FakeNavigationCommunication : NavigationCommunication.Update {

    fun check(screen: Screen)

    class Base(private val functionsCallsStack: FunctionsCallsStack) :
        FakeNavigationCommunication {

        private val list = mutableListOf<Screen>()
        private var index = 0

        override fun check(screen: Screen) {
            Assert.assertEquals(screen, list[index++])
            functionsCallsStack.checkCalled(MAP_CALL)
        }

        override fun map(data: Screen) {
            functionsCallsStack.put(MAP_CALL)
            list.add(data)
        }

        companion object {
            private const val MAP_CALL = "NavigationCommunication.Update#map"
        }
    }

}