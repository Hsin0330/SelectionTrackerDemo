package com.example.vox.recyclerviewselection

import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T, VH : RecyclerView.ViewHolder>(val items: List<T>) : RecyclerView.Adapter<VH>() {

    override fun getItemCount(): Int {
        return items.size
    }
}