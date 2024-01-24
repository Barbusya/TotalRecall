package com.bbbrrr8877.totalrecall.cardsList.presentation

import android.os.Bundle
import android.view.View
import com.bbbrrr8877.totalrecall.R
import com.bbbrrr8877.totalrecall.core.BaseFragment

class CardsListFragment : BaseFragment<CardsListViewModel>(R.layout.faragment_cards_list) {

    override val viewModelClass = CardsListViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}