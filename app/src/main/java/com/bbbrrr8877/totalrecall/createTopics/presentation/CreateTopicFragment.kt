package com.bbbrrr8877.totalrecall.createTopics.presentation

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

class CreateTopicFragment : BaseFragment<CreateTopicsViewModel>(R.layout.fragment_create_topic) {

    override val viewModelClass = CreateTopicsViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backButton = view.findViewById<View>(R.id.backButton)
        val progressBar = view.findViewById<View>(R.id.progressBar)
        val createButton = view.findViewById<Button>(R.id.createTopicButton)
        val textInputLayout = view.findViewById<TextInputLayout>(R.id.createTopicInputLayout)
        val textInputEditText = view.findViewById<TextInputEditText>(R.id.createTopicEditText)

        textInputEditText.addTextChangedListener(CreateTopicTextWatcher(viewModel))

        backButton.setOnClickListener {
            viewModel.goBack()
        }
        createButton.setOnClickListener {
            viewModel.createTopic(textInputEditText.text.toString())
        }
        viewModel.liveData().observe(viewLifecycleOwner) {
            it.show(progressBar, createButton, textInputLayout)
        }
    }
}

private class CreateTopicTextWatcher(
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