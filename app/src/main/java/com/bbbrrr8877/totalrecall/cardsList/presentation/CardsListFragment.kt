package com.bbbrrr8877.totalrecall.cardsList.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bbbrrr8877.totalrecall.R
import com.bbbrrr8877.totalrecall.core.BaseFragment
import com.bbbrrr8877.totalrecall.databinding.FragmentCardsListBinding

class CardsListFragment : BaseFragment<CardsListViewModel>(R.layout.fragment_cards_list) {

    override val viewModelClass = CardsListViewModel::class.java

    private var _binding: FragmentCardsListBinding? = null
    private val binding get() = _binding!!

    private lateinit var cardListAdapter: CardsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardListAdapter = CardsListAdapter()

        with(binding.cardsRecyclerView) {
            adapter = cardListAdapter
            swipeToAction(this)
        }

        with(binding) {
            cardsListToolbarTitle.text = viewModel.toolbarText()
            settingsButton.setOnClickListener { viewModel.showProfile() }
            backToTopicsButton.setOnClickListener { viewModel.goBack() }
            createTopicButton.setOnClickListener { viewModel.create() }
        }

        viewModel.liveData().observe(viewLifecycleOwner) {
            it.show(cardListAdapter)
        }

        viewModel.init(savedInstanceState == null)

    }

    private fun swipeToAction(recyclerView: RecyclerView) {
        val itemToLearn = { positionForRemove: Int ->
            val item = cardListAdapter.cardsList[positionForRemove]
            item.learnedCard(viewModel)
        }
        val swipeToLearn = SwipeToLearn(itemToLearn)
        ItemTouchHelper(swipeToLearn).attachToRecyclerView(recyclerView)

        val itemToReset = { positionForRemove: Int ->
            val item = cardListAdapter.cardsList[positionForRemove]
            item.resetCard(viewModel)
        }
        val swipeToReset = SwipeToReset(itemToReset)
        ItemTouchHelper(swipeToReset).attachToRecyclerView(recyclerView)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
