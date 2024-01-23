package com.bbbrrr8877.totalrecall.createTopics.presentation

import android.view.View
import android.widget.Button
import com.google.android.material.textfield.TextInputLayout

interface CreateTopicUiState {

    fun show(progressBar: View, createButton: Button, textInputLayout: TextInputLayout)

    abstract class Abstract(
        private val progressVisible: Boolean,
        private val createButtonEnabled: Boolean,
        private val error: String = ""
    ) : CreateTopicUiState {

        override fun show(
            progressBar: View,
            createButton: Button,
            textInputLayout: TextInputLayout
        ) {
            progressBar.visibility = if (progressVisible) View.VISIBLE else View.GONE
            createButton.isEnabled = createButtonEnabled
            textInputLayout.error = error
            textInputLayout.isErrorEnabled = error.isNotEmpty()
        }
    }

    object Progress : Abstract(true, false)

    object CanCreateTopic : Abstract(false, true)

    object CanNotCreateTopic : Abstract(false, false)

    data class TopicAlreadyExists(private val value: String) :
        Abstract(false, false, value)

    data class Error(val errorMessage: String) :
        Abstract(false, false, errorMessage) {

    }
}