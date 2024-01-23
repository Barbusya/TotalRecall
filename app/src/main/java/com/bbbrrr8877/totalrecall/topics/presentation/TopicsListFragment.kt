package com.bbbrrr8877.totalrecall.topics.presentation

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bbbrrr8877.totalrecall.R
import com.bbbrrr8877.totalrecall.core.BaseFragment

class TopicsListFragment : BaseFragment<TopicsViewModel>(R.layout.fragment_topics) {

    override val viewModelClass = TopicsViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TopicsListAdapter(viewModel)
        view.findViewById<RecyclerView>(R.id.topicsRecyclerView).adapter = adapter
        view.findViewById<View>(R.id.settingsButton).setOnClickListener {
            viewModel.showProfile()
        }
        view.findViewById<View>(R.id.createButton).setOnClickListener {
            viewModel.createTopic()
        }

        viewModel.observe(this) {
            it.show(adapter)
        }

        viewModel.init(savedInstanceState == null)
    }
}