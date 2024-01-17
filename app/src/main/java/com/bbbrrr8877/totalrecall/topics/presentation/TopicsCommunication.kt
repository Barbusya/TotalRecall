package com.bbbrrr8877.totalrecall.topics.presentation

import com.bbbrrr8877.totalrecall.core.Communication

interface TopicsCommunication : Communication.Mutable<TopicsUiState> {
    class Base : Communication.Abstract<TopicsUiState>(), TopicsCommunication
}