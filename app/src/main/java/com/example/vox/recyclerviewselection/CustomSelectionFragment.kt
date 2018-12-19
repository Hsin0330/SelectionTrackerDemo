package com.example.vox.recyclerviewselection

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_custom_selection.*

class CustomSelectionFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_custom_selection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CustomAdapter(people)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }
}

class CustomAdapter(items: List<Person>) : BaseAdapter<Person, ItemViewHolder>(items) {

    private var editMode = false
    private var selectedItems = mutableListOf<Person>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val viewHolder = ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_view_holder, parent, false))
        viewHolder.onLongClickListener = { view, position ->
            editMode = true
            selectedItems.add(items[position])
            notifyItemChanged(position)
        }

        viewHolder.onClickListener = { view, position ->
            if (editMode) {
                val item = items[position]
                if (selectedItems.contains(item)) {
                    selectedItems.remove(item)
                } else {
                    selectedItems.add(items[position])
                }
                notifyItemChanged(position)
            } else {
                // Other things the click need to do
            }
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val person = items[position]

        holder.numberText.text = "# ${person.id}"
        holder.nameText.text = person.name
        holder.selectionView.setBackgroundColor(
                if (selectedItems.contains(person))
                    Color.parseColor("#ff4081")
                else
                    Color.parseColor("#dedede"))
    }
}