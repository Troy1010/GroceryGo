package com.example.grocerygo.activities_and_frags.Inheritables

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
        val v = inflater.inflate(layout, container, false)
        onCreateViewInit()
        return v
    }

    open fun onCreateViewInit() {}
}