package com.bbbrrr8877.totalrecall

import com.bbbrrr8877.android.DispatchersList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher

class FakeDispatchersList(
    private val dispatcher: CoroutineDispatcher = TestCoroutineDispatcher()
) : DispatchersList {

    override fun io(): CoroutineDispatcher = dispatcher
    override fun ui(): CoroutineDispatcher = dispatcher
}