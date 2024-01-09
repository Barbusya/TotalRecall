package com.bbbrrr8877.totalrecall.topics.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bbbrrr8877.totalrecall.R
import com.bbbrrr8877.totalrecall.core.Mapper
import com.bbbrrr8877.totalrecall.core.Retry

class TopicsListAdapter(
    private val clickListener: TopicsClickListener
) : RecyclerView.Adapter<TopicsViewHolder>(), Mapper.Unit<List<TopicUi>> {

    private val topicsList = mutableListOf<TopicUi>()

    override fun getItemViewType(position: Int) =
        topicsList[position].orderId()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        2 -> TopicViewHolder(
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

    override fun onBindViewHolder(holder: TopicsViewHolder, position: Int) =
        holder.bind(topicsList[position])


    override fun getItemCount() = topicsList.size
    override fun map(data: List<TopicUi>) {
        val diff = DiffUtilCallback(topicsList, data)
        val result = DiffUtil.calculateDiff(diff)
        topicsList.clear()
        topicsList.addAll(data)
        result.dispatchUpdatesTo(this)
    }
}

open class TopicsViewHolder(
    view: View
) : RecyclerView.ViewHolder(view) {
    open fun bind(item: TopicUi) = Unit
}

private class TopicViewHolder(
    private val openTopic: OpenTopic,
    view: View
) : TopicsViewHolder(view) {

    private val button = itemView.findViewById<Button>(R.id.textViewTopicName)
    override fun bind(item: TopicUi) {
        item.map(button)
        button.setOnClickListener {
            item.openTopic(openTopic)
        }
    }
}

private class TopicErrorViewHolder(
    private val retry: Retry,
    view: View
) : TopicsViewHolder(view) {

    private val errorTextView = itemView.findViewById<TextView>(R.id.errorRetryTextView)
    private val retryButton = itemView.findViewById<Button>(R.id.retryButton)
    override fun bind(item: TopicUi) {
        item.map(errorTextView)
        retryButton.setOnClickListener {
            retry.retry()
        }
    }
}


interface TopicsClickListener : Retry, OpenTopic

interface OpenTopic {
    fun openTopic(topicList: TopicInfo)
}

private class DiffUtilCallback(
    private val oldList: List<TopicUi>,
    private val newList: List<TopicUi>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id() == newList[newItemPosition].id()

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}