package com.bbbrrr8877.totalrecall.topics.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.bbbrrr8877.totalrecall.core.BaseViewModel
import com.bbbrrr8877.totalrecall.core.Communication
import com.bbbrrr8877.totalrecall.core.DispatchersList
import com.bbbrrr8877.totalrecall.core.Init
import com.bbbrrr8877.totalrecall.core.ProvideError
import com.bbbrrr8877.totalrecall.core.Reload
import com.bbbrrr8877.totalrecall.cardsList.presentation.CreateTopicScreen
import com.bbbrrr8877.totalrecall.main.NavigationCommunication
import com.bbbrrr8877.totalrecall.profile.presentation.ProfileScreen
import com.bbbrrr8877.totalrecall.topics.data.TopicsRepository

class TopicsViewModel(
    private val navigation: NavigationCommunication.Update,
    dispatchersList: DispatchersList,
    private val topicsRepository: TopicsRepository,
    private val communication: TopicsListCommunication,
) : BaseViewModel(dispatchersList), TopicsViewModelActions {

    override fun observe(owner: LifecycleOwner, observer: Observer<TopicsListUiState>) {
        communication.observe(owner, observer)
    }

    override fun init(firstRun: Boolean) {
        communication.map(TopicsListUiState.Progress)
        topicsRepository.init(this)
    }

    override fun error(message: String) = communication.map(TopicsListUiState.Error(message))

    override fun reload() {
        handle({ topicsRepository.data() }) {
            communication.map(TopicsListUiState.Base(it))
        }
    }

    override fun retry() {
        communication.map(TopicsListUiState.Progress)
        reload()
    }

    override fun openTopic(topicInfo: TopicInfo) {
        topicsRepository.save(topicInfo)
        // todo navigation.map(TopicScreen)
    }

    override fun showProfile() = navigation.map(ProfileScreen)

    override fun createTopic() = navigation.map(CreateTopicScreen)
}

interface TopicsViewModelActions : Init, Communication.Observe<TopicsListUiState>,
    TopicsClickListener,
    ReloadWithError {
        fun createTopic()
        fun showProfile()
    }

interface ReloadWithError : Reload, ProvideError