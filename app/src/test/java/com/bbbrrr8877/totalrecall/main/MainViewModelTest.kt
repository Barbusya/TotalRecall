package com.bbbrrr8877.totalrecall.main

import com.bbbrrr8877.totalrecall.login.presentation.LoginScreen
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class MainViewModelTest {

    private lateinit var navigation: FakeNavigationCommunication
    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setup() {
        navigation = FakeNavigationCommunication()
        mainViewModel = MainViewModel(navigation)
    }


    @Test
    fun `test navigation at start`() {
        mainViewModel.init(true)
        val expected = LoginScreen
        navigation.checkLoginScreen(expected)
        navigation.checkInitCalledCount(1)
    }

}

class FakeNavigationCommunication : NavigationCommunication.Mutable {

    val navigationMappedList = mutableListOf<Screen>()

    fun checkInitCalledCount(value: Int) {
        assertEquals(value, navigationMappedList.size)
    }

    fun checkLoginScreen(value: Screen) {
        assertEquals(value, navigationMappedList[0])
    }

    override fun map(source: Screen) {
        navigationMappedList.add(source)
    }

}