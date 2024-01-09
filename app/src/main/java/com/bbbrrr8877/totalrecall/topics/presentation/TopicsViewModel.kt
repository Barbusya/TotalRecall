package com.bbbrrr8877.totalrecall.topics.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.bbbrrr8877.totalrecall.core.BaseViewModel
import com.bbbrrr8877.totalrecall.core.Communication
import com.bbbrrr8877.totalrecall.core.DispatchersList
import com.bbbrrr8877.totalrecall.core.Init
import com.bbbrrr8877.totalrecall.core.ProvideError
import com.bbbrrr8877.totalrecall.core.Reload
import com.bbbrrr8877.totalrecall.main.NavigationCommunication
import com.bbbrrr8877.totalrecall.profile.presentation.ProfileScreen
import com.bbbrrr8877.totalrecall.topics.data.TopicsRepository

class TopicsViewModel(
    private val navigation: NavigationCommunication.Update,
    dispatchersList: DispatchersList,
    private val topicsRepository: TopicsRepository,
    private val communication: TopicsCommunication,
) : BaseViewModel(dispatchersList), TopicsViewModelActions {

    override fun observe(owner: LifecycleOwner, observer: Observer<TopicsUiState>) {
        communication.observe(owner, observer)
    }

    override fun init(firstRun: Boolean) {
        communication.map(TopicsUiState.Progress)
        topicsRepository.init(this)
    }

    override fun error(message: String) = communication.map(TopicsUiState.Error(message))

    override fun reload() {
        handle({ topicsRepository.data() }) {
            communication.map(TopicsUiState.Base(it))
        }
    }

    override fun retry() {
        communication.map(TopicsUiState.Progress)
        reload()
    }

    override fun openTopic(topicInfo: TopicInfo) {
        topicsRepository.save(topicInfo)
        // todo navigation.map(TopicScreen)
    }

    fun showProfile() = navigation.map(ProfileScreen)
}

interface TopicsViewModelActions : Init, Communication.Observe<TopicsUiState>, TopicsClickListener,
        ReloadWithError

interface ReloadWithError : Reload, ProvideError