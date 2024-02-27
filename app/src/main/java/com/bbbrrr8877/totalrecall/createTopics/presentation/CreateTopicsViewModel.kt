package com.bbbrrr8877.totalrecall.createTopics.presentation

import com.bbbrrr8877.common.CreateUiActions
import com.bbbrrr8877.common.GoBack
import com.bbbrrr8877.totalrecall.R
import com.bbbrrr8877.totalrecall.core.BaseViewModel
import com.bbbrrr8877.totalrecall.core.Communication
import com.bbbrrr8877.totalrecall.core.DispatchersList
import com.bbbrrr8877.totalrecall.core.ManageResource
import com.bbbrrr8877.totalrecall.createTopics.data.CreateTopicsRepository
import com.bbbrrr8877.totalrecall.main.NavigationCommunication
import com.bbbrrr8877.totalrecall.topics.presentation.TopicsListScreen

class CreateTopicsViewModel(
    private val repository: CreateTopicsRepository,
    private val manageResource: ManageResource,
    private val communication: CreateTopicsCommunication,
    private val navigation: NavigationCommunication.Update,
    dispatchersList: DispatchersList,
) : BaseViewModel(dispatchersList), CreateTopicActions {


    override fun disableCreate() =
        communication.map(CreateTopicUiState.CanNotCreateTopic)

    override fun goBack() = navigation.map(TopicsListScreen)

    override fun liveData() = communication.liveData()

    override fun checkText(name: String) = communication.map(
        if (repository.contains(name))
            CreateTopicUiState.TopicAlreadyExists(manageResource.string(R.string.topic_already_exists))
        else
            CreateTopicUiState.CanCreateTopic
    )

    override fun createTopic(name: String) {
        communication.map(CreateTopicUiState.Progress)
        handle({ repository.create(name) }) {
            it.map(communication, navigation)
        }
    }
}

interface CreateTopicActions : CreateUiActions, GoBack, Communication.Observe<CreateTopicUiState> {
    fun createTopic(name: String)
}

