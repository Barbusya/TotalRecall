package com.bbbrrr8877.totalrecall.topics.presentation

import android.os.Bundle
import android.view.View
import com.bbbrrr8877.totalrecall.R
import com.bbbrrr8877.totalrecall.core.BaseFragment

class TopicsFragment : BaseFragment<TopicsViewModel>(R.layout.fragment_topics) {

    override val viewModelClass = TopicsViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.observe(this) {

        }

        viewModel.init(savedInstanceState == null)
    }
}