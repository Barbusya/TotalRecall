package com.bbbrrr8877.totalrecall.createCard.presentation

import android.view.View
import android.widget.Button
import com.google.android.material.textfield.TextInputLayout

interface CreateCardUiState {

    fun show(
        progressBar: View,
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
            progressBar: View,
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

    object CanCreateCard : Abstract (false, true)

    object CanNotCreateCard : Abstract(false, false)

    object Progress : Abstract(true, false)

    data class Error(private val errorMessage: String) :
            Abstract(false, false, errorMessage)
}