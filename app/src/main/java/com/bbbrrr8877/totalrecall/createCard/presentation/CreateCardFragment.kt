package com.bbbrrr8877.totalrecall.createCard.presentation

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bbbrrr8877.totalrecall.R
import com.bbbrrr8877.totalrecall.core.BaseFragment
import com.bbbrrr8877.totalrecall.core.CreateUiActions
import com.bbbrrr8877.totalrecall.core.SimpleTextWatcher
import com.bbbrrr8877.totalrecall.databinding.FragmentCreateCardBinding


class CreateCardFragment : BaseFragment<CreateCardViewModel>(R.layout.fragment_create_card) {

    override val viewModelClass = CreateCardViewModel::class.java

    private var _binding: FragmentCreateCardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            backToCardsButton.setOnClickListener { viewModel.goBack() }
            createCardButton.setOnClickListener {
                viewModel.createCard(
                    createCardAnswerEditText.text.toString(),
                    createCardClueEditText.text.toString()
                )
            }
            createCardAnswerEditText.addTextChangedListener(CreateCardTextWatcher(viewModel))
        }

        viewModel.liveData().observe(viewLifecycleOwner) {
            it.show(
                binding.progressBar,
                binding.createCardButton,
                binding.createCardAnswerInputLayout,
                binding.createCardClueInputLayout
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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