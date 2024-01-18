package com.bbbrrr8877.totalrecall.topics.presentation

import com.bbbrrr8877.totalrecall.core.Communication

interface TopicsListCommunication : Communication.Mutable<TopicsListUiState> {
    class Base : Communication.Abstract<TopicsListUiState>(), TopicsListCommunication
}