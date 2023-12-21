package com.bbbrrr8877.totalrecall.topics.presentation

import com.bbbrrr8877.totalrecall.core.BaseViewModel
import com.bbbrrr8877.totalrecall.core.Communication
import com.bbbrrr8877.totalrecall.core.DispatchersList
import com.bbbrrr8877.totalrecall.core.Init
import com.bbbrrr8877.totalrecall.core.ManageResource
import com.bbbrrr8877.totalrecall.main.NavigationCommunication

class TopicsViewModel(
    dispatchersList: DispatchersList,
    private val manageResource: ManageResource,
    private val communication: TopicsCommunication,
    private val navigation: NavigationCommunication.Update
    ) : BaseViewModel(dispatchersList), Init, Communication.Observe<TopicsUiState> {
    override fun init(firstRun: Boolean) {
        TODO("Not yet implemented")
    }
}