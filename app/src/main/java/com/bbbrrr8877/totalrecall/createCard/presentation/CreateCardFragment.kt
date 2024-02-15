package com.bbbrrr8877.totalrecall.createCard.presentation

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import com.bbbrrr8877.totalrecall.R
import com.bbbrrr8877.totalrecall.core.BaseFragment
import com.bbbrrr8877.totalrecall.core.CreateUiActions
import com.bbbrrr8877.totalrecall.core.SimpleTextWatcher
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class CreateCardFragment : BaseFragment<CreateCardViewModel>(R.layout.fragment_create_card) {

    override val viewModelClass = CreateCardViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backButton = view.findViewById<View>(R.id.backToCardsButton)
        val progressBar = view.findViewById<View>(R.id.progressBar)
        val createButton = view.findViewById<Button>(R.id.createCardButton)
        val answerInputLayout = view.findViewById<TextInputLayout>(R.id.createCardAnswerInputLayout)
        val answerInputEditText =
            view.findViewById<TextInputEditText>(R.id.createCardAnswerEditText)
        val clueInputLayout = view.findViewById<TextInputLayout>(R.id.createCardClueInputLayout)
        val clueInputEditText = view.findViewById<TextInputEditText>(R.id.createCardClueEditText)

        answerInputEditText.addTextChangedListener(CreateCardTextWatcher(viewModel))

        backButton.setOnClickListener {
            viewModel.goBack()
        }

        createButton.setOnClickListener {
            viewModel.createCard(
                answerInputEditText.text.toString(),
                clueInputEditText.text.toString()
            )
        }

        viewModel.liveData().observe(viewLifecycleOwner) {
            it.show(progressBar, createButton, answerInputLayout, clueInputLayout)
        }
    }
}

private class CreateCardTextWatcher(
    private val actions: CreateUiActions
) : SimpleTextWatcher() {

    override fun afterTextChanged(p0: Editable?) = with(actions) {
        val text = p0.toString()
        if (text.length >= minimumLength)
            checkText(text)
        else
            disableCreate()
    }
    companion object {
        private const val minimumLength: Int = 1
    }
}