package com.bbbrrr8877.totalrecall.topics.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bbbrrr8877.totalrecall.R
import com.bbbrrr8877.totalrecall.cardsList.presentation.CardsInfo
import com.bbbrrr8877.totalrecall.core.Mapper
import com.bbbrrr8877.totalrecall.core.Retry

class TopicsListAdapter(
    private val clickListener: TopicsClickListener
) : RecyclerView.Adapter<TopicViewHolder>(), Mapper.Unit<List<TopicListUi>> {

    private val topicsList = mutableListOf<TopicListUi>()

    override fun getItemViewType(position: Int) =
        topicsList[position].orderId()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        0 -> TopicViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_progress, parent, false)
        )
        2, 5 -> TopicNameViewHolder(
            clickListener,
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_topic, parent, false)
        )

        7 -> TopicErrorViewHolder(
            clickListener,
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_error, parent, false)
        )

        else -> throw IllegalStateException("unknown viewType $viewType")
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) =
        holder.bind(topicsList[position])


    override fun getItemCount() = topicsList.size
    override fun map(data: List<TopicListUi>) {
        val diff = TopicsListDiffUtilCallback(topicsList, data)
        val result = DiffUtil.calculateDiff(diff)
        topicsList.clear()
        topicsList.addAll(data)
        result.dispatchUpdatesTo(this)
    }
}

open class TopicViewHolder(view: View) : ViewHolder(view) {
    open fun bind(item: TopicListUi) = Unit
}

private class TopicNameViewHolder(
    private val openTopic: OpenTopic,
    view: View
) : TopicViewHolder(view) {

    private val button = itemView.findViewById<Button>(R.id.topicNameButton)
    override fun bind(item: TopicListUi) {
        item.map(button)
        button.setOnClickListener {
            item.openTopic(openTopic)
        }
    }
}

private class TopicErrorViewHolder(
    private val retry: Retry,
    view: View
) : TopicViewHolder(view) {

    private val errorTextView = itemView.findViewById<TextView>(R.id.errorRetryTextView)
    private val retryButton = itemView.findViewById<Button>(R.id.retryButton)
    override fun bind(item: TopicListUi) {
        item.map(errorTextView)
        retryButton.setOnClickListener {
            retry.retry()
        }
    }
}


interface TopicsClickListener : Retry, OpenTopic

interface OpenTopic {
    fun openTopic(topicInfo: TopicInfo)
}

private class TopicsListDiffUtilCallback(
    private val oldList: List<TopicListUi>,
    private val newList: List<TopicListUi>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id() == newList[newItemPosition].id()

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}