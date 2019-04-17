package com.jccword.behaviour.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import com.jccword.behaviour.R
import com.jccword.behaviour.domain.Child
import kotlinx.android.synthetic.main.child_tile.view.*

class ChildrenAdapter(private val context: Context?, hasTracker: Boolean) : RecyclerView.Adapter<ChildViewHolder>() {
    val children = mutableListOf<Child>()
    var tracker: SelectionTracker<Long>? = null

    init {
        setHasStableIds(hasTracker)
    }

    override fun getItemId(position: Int) = children[position].id!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        return ChildViewHolder(LayoutInflater.from(context).inflate(R.layout.child_tile, parent, false))
    }

    override fun getItemCount()= children.size

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        val child = children[position]
        val selected = tracker?.isSelected(child.id) ?: false

        holder.bind(child, selected)
    }

    fun update(children: List<Child>) {
        this.children.clear()
        this.children.addAll(children)
        notifyDataSetChanged()
    }
}

class ChildViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val name = view.name!!
    private val selected = view.selected!!

    fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition()= adapterPosition
                override fun getSelectionKey() = itemId
                override fun inSelectionHotspot(e: MotionEvent) = true
            }

    fun bind(child: Child, activated: Boolean = false) {
        itemView.isSelected = activated
        name.text = child.firstName
        itemView.isActivated = activated
        selected.visibility = if (activated) View.VISIBLE else View.GONE
    }
}

class ChildViewHolderItemDetailsLookup(private val recyclerView: RecyclerView): ItemDetailsLookup<Long>() {
    override fun getItemDetails(event: MotionEvent): ItemDetails<Long>? {
        val view = recyclerView.findChildViewUnder(event.x, event.y)
        view?.let {
            return (recyclerView.getChildViewHolder(view) as ChildViewHolder).getItemDetails()
        }

        return null
    }
}