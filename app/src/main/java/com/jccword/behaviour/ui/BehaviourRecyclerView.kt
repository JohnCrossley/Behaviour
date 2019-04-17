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
import com.jccword.behaviour.domain.Behaviour
import kotlinx.android.synthetic.main.behaviour_tile.view.*

class BehaviourAdapter(private val context: Context?, hasTracker: Boolean) : RecyclerView.Adapter<BehaviourViewHolder>() {
    val behaviours = mutableListOf<Behaviour>()
    var tracker: SelectionTracker<Long>? = null

    init {
        setHasStableIds(hasTracker)
    }

    override fun getItemId(position: Int) = behaviours[position].id!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BehaviourViewHolder {
        return BehaviourViewHolder(LayoutInflater.from(context).inflate(R.layout.behaviour_tile, parent, false))
    }

    override fun getItemCount()= behaviours.size

    override fun onBindViewHolder(holder: BehaviourViewHolder, position: Int) {
        val behaviour = behaviours[position]
        val selected = tracker?.isSelected(behaviour.id) ?: false

        holder.bind(behaviour, selected)
    }

    fun update(behaviours: List<Behaviour>) {
        this.behaviours.clear()
        this.behaviours.addAll(behaviours)
        notifyDataSetChanged()
    }
}

class BehaviourViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val name = view.name!!
    private val selected = view.selected!!

    fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition()= adapterPosition
                override fun getSelectionKey() = itemId
                override fun inSelectionHotspot(e: MotionEvent) = true
            }

    fun bind(behaviour: Behaviour, activated: Boolean = false) {
        itemView.isSelected = activated
        name.text = behaviour.name
        itemView.isActivated = activated
        selected.visibility = if (activated) View.VISIBLE else View.GONE
    }
}

class BehaviourViewHolderItemDetailsLookup(private val recyclerView: RecyclerView): ItemDetailsLookup<Long>() {
    override fun getItemDetails(event: MotionEvent): ItemDetails<Long>? {
        val view = recyclerView.findChildViewUnder(event.x, event.y)
        view?.let {
            return (recyclerView.getChildViewHolder(view) as BehaviourViewHolder).getItemDetails()
        }

        return null
    }
}