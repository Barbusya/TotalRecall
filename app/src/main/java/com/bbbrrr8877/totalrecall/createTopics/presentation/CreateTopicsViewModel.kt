package com.bbbrrr8877.totalrecall.createTopics.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.bbbrrr8877.totalrecall.core.BaseViewModel
import com.bbbrrr8877.totalrecall.core.Communication
import com.bbbrrr8877.totalrecall.core.DispatchersList
import com.bbbrrr8877.totalrecall.core.ManageResource
import com.bbbrrr8877.totalrecall.createTopics.data.CreateTopicsRepository
import com.bbbrrr8877.totalrecall.main.NavigationCommunication

class CreateTopicsViewModel(
    private val repository: CreateTopicsRepository,
    private val manageResource: ManageResource,
    private val communication: CreateTopicsCommunication,
    private val navigation: NavigationCommunication.Update,
    dispatchersList: DispatchersList,
) : BaseViewModel(dispatchersList), Communication.Observe<CreateTopicUiState> {


    fun disableCreate() =
        communication.map(CreateTopicUiState.CanNotCreateTopic)

    override fun observe(owner: LifecycleOwner, observer: Observer<CreateTopicUiState>) {
        communication.observe(owner, observer)
    }

    fun checkTopic(name: String) = communication.map(
        if (repository.contains(name))
            CreateTopicUiState.TopicAlreadyExists
        else
            CreateTopicUiState.CanCreateTopic
    )

    fun create(name: String) {
        communication.map(CreateTopicUiState.Progress)
        handle({ repository.create(name) }) {
            it.map(communication, navigation)
        }
    }
}