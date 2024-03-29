package com.bbbrrr8877.totalrecall.core

import androidx.lifecycle.ViewModel
import com.bbbrrr8877.totalrecall.cardsList.CardsListModule
import com.bbbrrr8877.totalrecall.cardsList.presentation.CardsListViewModel
import com.bbbrrr8877.totalrecall.createCard.CreateCardModule
import com.bbbrrr8877.totalrecall.createCard.presentation.CreateCardViewModel
import com.bbbrrr8877.totalrecall.createTopics.CreateTopicsModule
import com.bbbrrr8877.totalrecall.createTopics.presentation.CreateTopicsViewModel
import com.bbbrrr8877.totalrecall.login.LoginModule
import com.bbbrrr8877.totalrecall.login.presentation.LoginViewModel
import com.bbbrrr8877.totalrecall.main.MainModule
import com.bbbrrr8877.totalrecall.main.MainViewModel
import com.bbbrrr8877.totalrecall.profile.ProfileModule
import com.bbbrrr8877.totalrecall.profile.presentation.ProfileViewModel
import com.bbbrrr8877.totalrecall.topics.TopicsModule
import com.bbbrrr8877.totalrecall.topics.presentation.TopicsViewModel

interface DependencyContainer {

    fun module(className: Class<out ViewModel>): Module<out ViewModel>

    class Error : DependencyContainer {
        override fun module(className: Class<out ViewModel>): Module<out ViewModel> =
            throw IllegalStateException("unknown className $className")
    }

    class Base(
        private val core: Core,
        private val dependencyContainer: DependencyContainer = Error()
    ) : DependencyContainer {
        override fun module(className: Class<out ViewModel>) = when (className) {
            MainViewModel::class.java -> MainModule(core)
            LoginViewModel::class.java -> LoginModule(core)
            ProfileViewModel::class.java -> ProfileModule(core)
            TopicsViewModel::class.java -> TopicsModule(core)
            CreateTopicsViewModel::class.java -> CreateTopicsModule(core)
            CardsListViewModel::class.java -> CardsListModule(core)
            CreateCardViewModel::class.java -> CreateCardModule(core)
            else -> dependencyContainer.module(className)
        }
    }
}