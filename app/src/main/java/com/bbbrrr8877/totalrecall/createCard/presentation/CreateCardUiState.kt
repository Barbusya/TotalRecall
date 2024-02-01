package com.bbbrrr8877.totalrecall.createCard.presentation

import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import com.google.android.material.textfield.TextInputLayout

interface CreateCardUiState {

    fun show(
        progressBar: ProgressBar,
        createButton: Button,
        answerTextInputLayout: TextInputLayout,
        clueTextInputLayout: TextInputLayout
    )

    abstract class Abstract(
        private val progressVisible: Boolean,
        private val createButtonEnabled: Boolean,
        private val error: String = ""
    ) : CreateCardUiState {
        override fun show(
            progressBar: ProgressBar,
            createButton: Button,
            answerTextInputLayout: TextInputLayout,
            clueTextInputLayout: TextInputLayout
        ) {
            progressBar.visibility = if (progressVisible) View.VISIBLE else View.GONE
            createButton.isEnabled = (createButtonEnabled)
            answerTextInputLayout.error = error
            answerTextInputLayout.isErrorEnabled = error.isNotEmpty()
        }
    }

    object Progress : Abstract(true, false)

    data class Error(private val errorMessage: String) :
            Abstract(false, false, errorMessage)
}