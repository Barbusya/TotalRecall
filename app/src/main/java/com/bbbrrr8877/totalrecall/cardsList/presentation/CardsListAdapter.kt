package com.bbbrrr8877.totalrecall.cardsList.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bbbrrr8877.totalrecall.R
import com.bbbrrr8877.totalrecall.core.Mapper

class CardsListAdapter(
    private val swipeListener: SwipeListener
) : RecyclerView.Adapter<CardsListViewHolder>(),
    Mapper.Unit<List<CardsListUi>> {

    val cardsList = mutableListOf<CardsListUi>()

    override fun getItemViewType(position: Int): Int =
        cardsList[position].orderId()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        0 -> CardsListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_progress, parent, false)
        )

        2 -> CardViewHolder(
            swipeListener,
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_card, parent, false)
        )

        3 -> NoCardsHintViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_card, parent, false)
        )

        7 -> CardsErrorViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_card, parent, false)
        )

        else -> throw IllegalStateException("unknown viewType $viewType")
    }

    override fun getItemCount() = cardsList.size

    override fun onBindViewHolder(holder: CardsListViewHolder, position: Int) {
        holder.bind(cardsList[position])
    }

    override fun map(data: List<CardsListUi>) {
        val diff = CardsListItemDiffCallback(cardsList, data)
        val result = DiffUtil.calculateDiff(diff)
        cardsList.clear()
        cardsList.addAll(data)
        result.dispatchUpdatesTo(this)
    }

}

open class CardsListViewHolder(view: View) : ViewHolder(view) {
    open fun bind(item: CardsListUi) = Unit
}

private class CardViewHolder(
    private val swipeListener: SwipeListener,
    view: View
) : CardsListViewHolder(view) {

    private val parentVew = itemView.findViewById<View>(R.id.cardViewGroup)
    private val tvAnswer = itemView.findViewById<TextView>(R.id.textViewAnswer)
    private val tvClue = itemView.findViewById<TextView>(R.id.textViewClue)
    private val learnedButton = itemView.findViewById<Button>(R.id.learnedButton)
    private val resetButton = itemView.findViewById<Button>(R.id.resetButton)

    override fun bind(item: CardsListUi) {
        item.map(tvAnswer, tvClue, parentVew)
        parentVew.setOnClickListener { item.showAnswer(tvAnswer) }
        learnedButton.setOnClickListener { item.learnedCard(swipeListener) }
        resetButton.setOnClickListener { item.resetCard(swipeListener) }
    }

}

private class NoCardsHintViewHolder(
    view: View
) : CardsListViewHolder(view) {

    private val parentVew = itemView.findViewById<View>(R.id.cardViewGroup)
    private val tvAnswer = itemView.findViewById<TextView>(R.id.textViewAnswer)
    private val tvClue = itemView.findViewById<TextView>(R.id.textViewClue)

    override fun bind(item: CardsListUi) {
        item.map(tvAnswer, tvClue, parentVew)
    }
}

private class CardsErrorViewHolder(
    view: View
) : CardsListViewHolder(view) {
    private val parentVew = itemView.findViewById<View>(R.id.cardViewGroup)
    private val tvAnswer = itemView.findViewById<TextView>(R.id.textViewAnswer)
    private val tvClue = itemView.findViewById<TextView>(R.id.textViewClue)

    override fun bind(item: CardsListUi) {
        item.map(tvAnswer, tvClue, parentVew)
    }
}

private class CardsListItemDiffCallback(
    private val oldList: List<CardsListUi>,
    private val newList: List<CardsListUi>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id() == newList[newItemPosition].id()

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]

}
