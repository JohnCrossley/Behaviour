package com.jccworld.behaviour.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import com.jccworld.behaviour.R
import com.jccworld.behaviour.domain.Behaviour
import kotlinx.android.synthetic.main.behaviour_tile.view.*

class BehaviourAdapter(
    private val context: Context?,
    hasTracker: Boolean,
    private val selectedColour: Int,
    private val unselectedColour: Int
) : RecyclerView.Adapter<BehaviourViewHolder>() {
    private val behaviours = mutableListOf<Behaviour>()
    var tracker: SelectionTracker<Long>? = null

    init {
        setHasStableIds(hasTracker)
    }

    override fun getItemId(position: Int) = behaviours[position].id!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BehaviourViewHolder {
        return BehaviourViewHolder(LayoutInflater.from(context).inflate(R.layout.behaviour_tile, parent, false), selectedColour, unselectedColour)
    }

    override fun getItemCount() = behaviours.size

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

class BehaviourViewHolder(view: View, private val selectedColour: Int, private val unselectedColour: Int) : RecyclerView.ViewHolder(view) {
    val text = view.behaviour!!
    private val image = view.image!!

    fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition()= adapterPosition
                override fun getSelectionKey() = itemId
                override fun inSelectionHotspot(e: MotionEvent) = true
            }

    fun bind(behaviour: Behaviour, activated: Boolean = false) {
        itemView.isSelected = activated
        text.text = behaviour.name
        itemView.isActivated = activated
        image.contentDescription = behaviour.name

        when(activated) {
            true -> {
                image.setColorFilter(selectedColour)
                text.setTextColor(selectedColour)
            }
            false -> {
                image.setColorFilter(unselectedColour)
                text.setTextColor(unselectedColour)
            }
        }
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