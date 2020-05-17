package com.example.grocerygo.inheritables

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class TMFragment: Fragment() {
    abstract val layout: Int
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layout, container, false)
    }

    override fun onStart() {
        super.onStart()
        val activityZ = activity
        if (activityZ is GGToolbarActivity) {
            activityZ.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }
}