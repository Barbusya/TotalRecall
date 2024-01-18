package com.bbbrrr8877.totalrecall.createTopics.presentation

import com.bbbrrr8877.totalrecall.core.Communication

interface CreateTopicsCommunication : Communication.Mutable<CreateTopicUiState> {
    class Base: Communication.Abstract<CreateTopicUiState>(), CreateTopicsCommunication
}