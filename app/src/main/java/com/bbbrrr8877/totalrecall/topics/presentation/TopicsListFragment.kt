package com.bbbrrr8877.totalrecall.topics.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bbbrrr8877.totalrecall.R
import com.bbbrrr8877.totalrecall.core.BaseFragment
import com.bbbrrr8877.totalrecall.databinding.FragmentTopicsBinding

class TopicsListFragment : BaseFragment<TopicsViewModel>(R.layout.fragment_topics) {

    override val viewModelClass = TopicsViewModel::class.java

    private var _binding: FragmentTopicsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTopicsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val topicsListAdapter = TopicsListAdapter(viewModel)
        with(binding) {
            topicsRecyclerView.adapter = topicsListAdapter
            settingsButton.setOnClickListener { viewModel.showProfile() }
            createTopicButton.setOnClickListener { viewModel.create() }
        }

        viewModel.liveData().observe(viewLifecycleOwner) {
            it.show(topicsListAdapter)
        }

        viewModel.init(savedInstanceState == null)
    }
}