package com.bbbrrr8877.totalrecall.createTopics.presentation

import android.os.Bundle
import android.view.View
import com.bbbrrr8877.totalrecall.R
import com.bbbrrr8877.totalrecall.core.BaseFragment

class CreateTopicFragment : BaseFragment<CreateTopicsViewModel>(R.layout.fragment_create_topic) {

    override val viewModelClass = CreateTopicsViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}