package com.bbbrrr8877.totalrecall.library.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bbbrrr8877.android.BaseFragment
import com.bbbrrr8877.totalrecall.R
import com.bbbrrr8877.totalrecall.databinding.FragmentLibraryBinding

class LibraryFragment : BaseFragment<LibraryViewModel>(R.layout.fragment_library) {

    override val viewModelClass = LibraryViewModel::class.java

    private var _binding: FragmentLibraryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val libraryAdapter = LibraryAdapter(viewModel)

        with(binding) {
            libraryRecyclerView.adapter = libraryAdapter
            settingsButton.setOnClickListener { viewModel.showProfile() }
            backToTopicsButton.setOnClickListener { viewModel.goBack() }
        }

        viewModel.liveData().observe(viewLifecycleOwner) {
            it.show(libraryAdapter)
        }
        viewModel.init(savedInstanceState == null)
    }
}