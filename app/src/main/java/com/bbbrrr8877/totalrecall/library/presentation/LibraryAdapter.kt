package com.bbbrrr8877.totalrecall.library.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bbbrrr8877.common.Mapper
import com.bbbrrr8877.common.Retry
import com.bbbrrr8877.totalrecall.R

class LibraryAdapter(
    private val clickListener: LoadClickListener
) : RecyclerView.Adapter<LibraryViewHolder>(), Mapper.Unit<List<LibraryUi>> {

    private val libraryList = mutableListOf<LibraryUi>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        0 -> LibraryViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_progress, parent, false)
        )

        2 -> LibraryItemViewHolder(
            clickListener,
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_library, parent, false)
        )

        3 -> NoLibraryHintViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_library, parent, false)
        )

        7 -> LibraryErrorViewHolder(
            clickListener,
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_error, parent, false)
        )

        else -> throw IllegalStateException("unknown viewType $viewType")
    }

    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) =
        holder.bind(libraryList[position])

    override fun getItemCount() = libraryList.size

    override fun map(data: List<LibraryUi>) {
        val diff = LibraryListDiffUtilCallback(libraryList, data)
        val result = DiffUtil.calculateDiff(diff)
        libraryList.clear()
        libraryList.addAll(data)
        result.dispatchUpdatesTo(this)
    }
}

open class LibraryViewHolder(view: View) : ViewHolder(view) {
    open fun bind(item: LibraryUi) = Unit
}

private class LibraryItemViewHolder(
    private val loadList: LoadList,
    view: View
) : LibraryViewHolder(view) {

    private val name = itemView.findViewById<TextView>(R.id.libraryNameTextView)
    private val description = itemView.findViewById<TextView>(R.id.libraryDescriptionTextView)
    private val loadButton = itemView.findViewById<View>(R.id.loadLibraryButton)
    override fun bind(item: LibraryUi) {
        item.map(name, description)
        loadButton.setOnClickListener {
            item.load(loadList)
        }
    }
}

private class NoLibraryHintViewHolder(
    view: View
) : LibraryViewHolder(view) {
    private val name = itemView.findViewById<TextView>(R.id.libraryNameTextView)
    private val description = itemView.findViewById<TextView>(R.id.libraryDescriptionTextView)
    override fun bind(item: LibraryUi) {
        item.map(name, description)
    }
}

private class LibraryErrorViewHolder(
    private val retry: Retry,
    view: View
) : LibraryViewHolder(view) {

    private val errorTextView = itemView.findViewById<TextView>(R.id.errorRetryTextView)
    private val textView = itemView.findViewById<TextView>(R.id.libraryDescriptionTextView)
    private val retryButton = itemView.findViewById<Button>(R.id.retryButton)
    override fun bind(item: LibraryUi) {
        item.map(errorTextView, textView)
        retryButton.setOnClickListener {
            retry.retry()
        }
    }
}

interface LoadClickListener : Retry, LoadList

interface LoadList {
    fun load(libraryItemInfo: LibraryItemInfo)
}

private class LibraryListDiffUtilCallback(
    private val oldList: List<LibraryUi>,
    private val newList: List<LibraryUi>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id() == newList[newItemPosition].id()

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}