package com.bbbrrr8877.totalrecall.cardsList.presentation

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bbbrrr8877.totalrecall.R
import com.bbbrrr8877.totalrecall.core.BaseFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CardsListFragment : BaseFragment<CardsListViewModel>(R.layout.faragment_cards_list) {

    override val viewModelClass = CardsListViewModel::class.java

    private lateinit var cardListAdapter: CardsListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardListAdapter = CardsListAdapter()
        val recyclerView = view.findViewById<RecyclerView>(R.id.cardsRecyclerView)
        recyclerView.adapter = cardListAdapter

        view.findViewById<View>(R.id.settingsButton).setOnClickListener {
            viewModel.showProfile()
        }
        view.findViewById<View>(R.id.backToTopicsButton).setOnClickListener {
            viewModel.goBack()
        }
        view.findViewById<FloatingActionButton>(R.id.createTopicButton).setOnClickListener {
            viewModel.create()
        }

        viewModel.liveData().observe(viewLifecycleOwner) {
            it.show(cardListAdapter)
        }

        viewModel.init(savedInstanceState == null)

        swipeToAction(recyclerView)

    }

    private fun swipeToAction(rvCardList: RecyclerView) {
        val itemToLearn = { positionForRemove: Int ->
            val item = cardListAdapter.cardsList[positionForRemove]
            item.learnedCard(viewModel)
        }
        val swipeToLearn = SwipeToLearn(itemToLearn)
        ItemTouchHelper(swipeToLearn).attachToRecyclerView(rvCardList)

        val itemToReset = { positionForRemove: Int ->
            val item = cardListAdapter.cardsList[positionForRemove]
            item.resetCard(viewModel)
        }
        val swipeToReset = SwipeToReset(itemToReset)
        ItemTouchHelper(swipeToReset).attachToRecyclerView(rvCardList)
    }

    class SwipeToLearn(
        val itemToLearn: (Int) -> Unit
    ) : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.ACTION_STATE_IDLE,
        ItemTouchHelper.UP
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ) = false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            itemToLearn(viewHolder.adapterPosition)
        }
    }

    class SwipeToReset(
        val itemToReset: (Int) -> Unit
    ) : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.ACTION_STATE_IDLE,
        ItemTouchHelper.DOWN
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ) = false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            itemToReset(viewHolder.adapterPosition)
        }
    }
}
