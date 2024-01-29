package com.bbbrrr8877.totalrecall.cardsList.presentation

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bbbrrr8877.totalrecall.R
import com.bbbrrr8877.totalrecall.core.BaseFragment

class CardsListFragment : BaseFragment<CardsListViewModel>(R.layout.faragment_cards_list) {

    override val viewModelClass = CardsListViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CardsListAdapter()
        view.findViewById<RecyclerView>(R.id.cardsRecyclerView).adapter = adapter
        view.findViewById<View>(R.id.settingsButton).setOnClickListener {
            viewModel.showProfile()
        }
        view.findViewById<View>(R.id.backToTopicsButton).setOnClickListener {
            viewModel.goBack()
        }
        view.findViewById<View>(R.id.createButton).setOnClickListener {
            viewModel.create()
        }

        viewModel.observe(this) {
            it.show(adapter)
        }

        viewModel.init(savedInstanceState == null)

    }

}