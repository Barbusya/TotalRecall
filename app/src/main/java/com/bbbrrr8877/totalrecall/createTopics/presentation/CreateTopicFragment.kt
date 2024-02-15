package com.bbbrrr8877.totalrecall.createTopics.presentation

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.bbbrrr8877.totalrecall.R
import com.bbbrrr8877.totalrecall.core.BaseFragment
import com.bbbrrr8877.totalrecall.core.CreateUiActions
import com.bbbrrr8877.totalrecall.core.SimpleTextWatcher
import com.bbbrrr8877.totalrecall.databinding.FragmentCreateCardBinding
import com.bbbrrr8877.totalrecall.databinding.FragmentCreateTopicBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class CreateTopicFragment : BaseFragment<CreateTopicsViewModel>(R.layout.fragment_create_topic) {

    override val viewModelClass = CreateTopicsViewModel::class.java

    private var _binding: FragmentCreateTopicBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateTopicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            backButton.setOnClickListener { viewModel.goBack() }
            createTopicButton.setOnClickListener {  viewModel.createTopic(
                createTopicEditText.text.toString())
            }
            createTopicEditText.addTextChangedListener(CreateTopicTextWatcher(viewModel))
        }

        viewModel.liveData().observe(viewLifecycleOwner) {
            it.show(binding.progressBar, binding.createTopicButton, binding.createTopicInputLayout)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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