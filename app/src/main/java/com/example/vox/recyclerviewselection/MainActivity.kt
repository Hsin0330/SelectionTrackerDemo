package com.example.vox.recyclerviewselection

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        customSelection.setOnClickListener {
            commitNewFragment(R.id.container, CustomSelectionFragment())
        }
        selectionTracker.setOnClickListener {
            commitNewFragment(R.id.container, SelectionTrackerFragment())
        }
    }

    private fun commitNewFragment(container: Int, fragment: Fragment) {
        val tag = fragment.javaClass.simpleName

        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
                .add(container, fragment, tag)
                .addToBackStack(tag)
                .commit()
    }
}


