package com.example.vox.recyclerviewselection

import android.view.View
import android.widget.TextView
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val selectionView: View = itemView.findViewById(R.id.selection)
    val numberText: TextView = itemView.findViewById(R.id.id)
    val nameText: TextView = itemView.findViewById(R.id.name)
    val itemDetails = object : ItemDetailsLookup.ItemDetails<Long>() {
        override fun getSelectionKey(): Long? {
            return itemId
        }

        override fun getPosition(): Int {
            return adapterPosition
        }

    }

    var onLongClickListener = { _: View, _: Int -> Unit }
        set(value) = itemView.setOnLongClickListener {
            value(it, adapterPosition)
            true
        }

    var onClickListener = { _: View, _: Int -> Unit }
        set(value) = itemView.setOnClickListener {
            value(it, adapterPosition)
        }
}