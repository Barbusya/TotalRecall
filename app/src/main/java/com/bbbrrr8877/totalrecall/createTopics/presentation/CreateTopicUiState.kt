package com.bbbrrr8877.totalrecall.createTopics.presentation

interface CreateTopicUiState {

    object Progress : CreateTopicUiState

    object CanCreateTopic : CreateTopicUiState

    object CanNotCreateTopic : CreateTopicUiState

    object TopicAlreadyExists : CreateTopicUiState

    data class Error(val errorMessage: String) : CreateTopicUiState {

    }
}