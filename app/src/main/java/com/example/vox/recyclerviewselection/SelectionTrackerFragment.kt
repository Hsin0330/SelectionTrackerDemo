package com.example.vox.recyclerviewselection

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_custom_selection.*

class SelectionTrackerFragment : Fragment() {

    private val MAXIMUM_SELECTION = 5

    private lateinit var selectionTracker: SelectionTracker<Long>
    private val itemDetailsLookup = object : ItemDetailsLookup<Long>() {
        override fun getItemDetails(e: MotionEvent): ItemDetails<Long>? {
            val view = recyclerView.findChildViewUnder(e.x, e.y) ?: return null

            val holder = recyclerView.getChildViewHolder(view)
            (holder as? ItemViewHolder)?.apply {
                return this.itemDetails
            }
            return null
        }
    }

//    private val selectionPredicate = object : SelectionTracker.SelectionPredicate<Long>() {
//        override fun canSelectMultiple(): Boolean {
//            return true
//        }
//
//        override fun canSetStateForKey(key: Long, nextState: Boolean): Boolean {
//            return if (selectionTracker.selection.size() >= MAXIMUM_SELECTION && nextState) {
//                Toast.makeText(activity,
//                        "You can only select $MAXIMUM_SELECTION items in the list.", Toast.LENGTH_SHORT).show()
//                false
//            } else {
//                true
//            }
//        }
//
//        override fun canSetStateAtPosition(position: Int, nextState: Boolean): Boolean {
//            return true
//        }
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_custom_selection, container, false)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        selectionTracker.onRestoreInstanceState(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        selectionTracker.onSaveInstanceState(outState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectionAdapter = SelectionAdapter(people)
        selectionAdapter.isSelect = {
            selectionTracker.isSelected(it)
        }

        recyclerView.apply {
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(activity)
            adapter = selectionAdapter
        }

        selectionTracker =
                SelectionTracker.Builder(
                        "selection_tracker",
                        recyclerView,
                        StableIdKeyProvider(recyclerView),
                        itemDetailsLookup,
                        StorageStrategy.createLongStorage())
//                        .withSelectionPredicate(selectionPredicate)
                        .build()
    }
}

class SelectionAdapter(items: List<Person>) : BaseAdapter<Person, ItemViewHolder>(items) {

    var isSelect = { key: Long -> false }

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_view_holder, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val person = items[position]

        holder.numberText.text = "# ${person.id}"
        holder.nameText.text = person.name
        holder.selectionView.setBackgroundColor(
                if (isSelect(getItemId(position)))
                    Color.parseColor("#ff4081")
                else
                    Color.parseColor("#dedede"))
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}